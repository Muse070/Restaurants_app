package com.example.restaurantsapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RestaurantsViewModel(
    private val stateHandle: SavedStateHandle
): ViewModel() {

    // Get the current state of the list.

    // mutableStateOf creates a MutableState object which holds a value
    // and triggers recomposition when the value changes.

    // .restoreSelections is an extension function which provides additional functionality for the list of restaurants
    // This call to .restoreSelections should restore the state of the toggles restaurant list items in the event
    // a process death
    val state = mutableStateOf(dummyRestaurants.restoreSelections())

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