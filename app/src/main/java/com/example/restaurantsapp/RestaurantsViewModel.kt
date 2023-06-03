package com.example.restaurantsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsViewModel(
    private val stateHandle: SavedStateHandle
): ViewModel() {

    private var restInterface: RestaurantApiService

    // Get the current state of the list.

    // mutableStateOf creates a MutableState object which holds a value
    // and triggers recomposition when the value changes.

    // .restoreSelections is an extension function which provides additional functionality for the list of restaurants
    // This call to .restoreSelections should restore the state of the toggles restaurant list items in the event
    // a process death
    val state = mutableStateOf(emptyList<Restaurant>())

    private lateinit var restaurantsCall: Call<List<Restaurant>>

    // Instantiate the Retrofit Builder object
    init {
        // Instantiate the retrofit: Retrofit with the Retrofit.Builder accessor
        val retrofit: Retrofit = Retrofit.Builder()
            // Tell Retrofit to deserialize JSON with the GSON converter
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            // Define the base url
            .baseUrl(
                "https://restaurant-app-5711a-default-rtdb.firebaseio.com/"
            )
            .build()
        // Store the instance of the created Retrofit Builder in the restInterface
        restInterface = retrofit.create(
            RestaurantApiService::class.java
        )
        // Because getRestaurants instantiates the state which is used in the composable screen,
        // getRestaurants is called in the init block to make the call request once.
        getRestaurants()
    }

    // In order to prevent memory leakage, we need to cancel the call request that was made in the event
    // that the user navigates to another screen before the call request is complete or during other instances.
    // onCleared cancels any requests that were made when the viewModel is no longer required.
    override fun onCleared() {
        super.onCleared()
        restaurantsCall.cancel()
    }

    // Execute the call request to GET a list of the restaurants available in the Firebase DB
    private fun getRestaurants() {
        // A call object is obtained from the Retrofit restInterface variable by calling restInterface
        // The execute()  method is a simple approach to starting a network request with Retrofit.
        // Execute() runs the request synchronously on the main thread and blocks it until the response arrives.
        // The execute() method returns a Retrofit Response object that allows us to see if the response
        // was successful and obtain the resulting body.
        // The body() accessor returns a nullable list of type <List<Restaurant>>?.
        // The list of restaurants is stored in a variable name restaurants
        // But we should never use the execute function!!!

        // On the call object obtained from the restInterface.getRestaurants() method, we called the enqueue() function
        // The enqueue() call is a better alternative to execute() because it runs the network requests asynchronously.
        restaurantsCall = restInterface.getRestaurants()

        restaurantsCall.enqueue(
            // The enqueue function receives a callback object which allows us to listen for success or failure callbacks.
            // The expected response type is defined by the callback's parameter.
            object : Callback<List<Restaurant>> {
                // onResponse is the success callback.
                override fun onResponse(
                    // onResponse provides the initial Call object and the Response object.
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    // Inside the callback, we get the body from the response and update the state variable.
                    response.body()?.let { restaurants ->
                        state.value = restaurants.restoreSelections()
                    }
                }

                // onFailure is the failure callback.
                // It's invoked when there is a network exception or when an unexpected exception occurs.
                // This callback provides us with the initial callback and the Throwable exception.
                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }


    fun toggleFavorite(id: Int) {
        // mutableListOf returns a MutableList which is a list that can be
        // modified by adding, removing or updating its elements

        // Create a new instance of the original list of restaurants
        val restaurants = state.value.toMutableList()

        // Collect the index of the desired restaurant item
        val itemIndex = restaurants.indexOfFirst {
            it.id == id
        }

        // Collect the desired item to be changed
        val item = restaurants[itemIndex]

        // change the isFavorite value of the clicked restaurant item
        restaurants[itemIndex] = item.copy(
            isFavorite = !item.isFavorite
        )

        storeSelection(restaurants[itemIndex])


        // Store the new list in place of the original list
        state.value = restaurants
    }

    // Create an extension function which extends any list of type Restaurant.
    // The purpose of this function is to get the most up to date data from the savedStateHandle
    // and return it if a process death occurred, or return the list unchanged if no process death occurred.
    private fun List<Restaurant>.restoreSelections(): List<Restaurant> {
        // Get the state of the list.
        // Null checks to observe and react when a process death has occurred
        stateHandle.get<List<Int>?>(FAVORITES)?.let {selectedIds ->
            // Create a map from the input list of restaurants
            // The key is the id
            // The value is the restaurant item itself
            val restaurantsMap = this.associateBy {
                it.id
            }
            // For each id associated with a restaurant, toggle the isFavorite variable to true
            // in order to restore the most recent state of the list
            selectedIds.forEach { id ->
                restaurantsMap[id]?.isFavorite = true
            }
            // return the list of the isFavorite values before the death process occurred.
            return restaurantsMap.values.toList()
        }
        return this
    }

    // Store the list of toggled restaurants in the SavedStateHandle
    // This function can be looked at as a modified set version of the set method
    // of the state handle.
    private fun storeSelection(item: Restaurant) {

        // Obtain a list of the previously favorite items from the restaurant list.
        // If no items were favorites, create a empty list.
        val savedToggled = stateHandle
            .get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableList()

        // When an item is a favorite, add the item to the new savedToggle list
        // Otherwise, if not (or no longer) toggled remove from the list.
        if (item.isFavorite)
            savedToggled.add(item.id)
        else
            savedToggled.remove(item.id)

        // replace contents of the stateHandle for the FAVORITES key with the contents of savedToggled
        stateHandle[FAVORITES] = savedToggled
    }
    companion object {
        // FAVORITES is a constant value for the key used to save the restaurant's selection inside
        // the stateHandle map.
        // This FAVORITES key is linked to the storage of all Ids.
        // Similarly, a different key can be used to store values of another data type.
        const val FAVORITES = "favorites"
    }

}