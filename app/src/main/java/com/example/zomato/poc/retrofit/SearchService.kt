package com.example.zomato.poc.retrofit

import com.example.zomato.poc.model.location.LocationResult
import com.example.zomato.poc.model.restaurant.SearchRestaurant
import retrofit2.Call
import retrofit2.http.*

interface SearchService {

    @GET("api/v2.1/locations")
    fun getLocation(
        @Header("user-key") userKey: String,
        @Query("query") query: String,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("count") count: Int
    ): Call<LocationResult>

    @GET("api/v2.1/search")
    fun getRestore(
        @Header("user-key") userKey: String,
        @Query("entity_id") entity_id: Int,
        @Query("entity_type") entity_type: String,
        @Query("count") count: Int?
    ): Call<SearchRestaurant>


}