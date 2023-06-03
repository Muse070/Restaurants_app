package com.example.restaurantsapp

import retrofit2.Call
import retrofit2.http.GET

// This interface defines the HTTP actions required
interface RestaurantApiService {

    // @GET informs retrofit that this method should execute a GET HTTP action.
    // Furthermore, the endpoints path is is passed as a parameter in the GET annotation.
    @GET("restaurants.json")
    // The getRestaurant function returns a Call object with an undefined response type.
    // Returning a call allows you to handle the request asynchronously.
    // This is useful because network requests take a while and this helps avoid blocking the request unnecessarily.
    fun getRestaurants(): Call<List<Restaurant>>
}