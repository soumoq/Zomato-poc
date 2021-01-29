package com.example.zomato.poc.retrofit

import com.example.zomato.poc.model.location.LocationResult
import retrofit2.Call
import retrofit2.http.*

interface SearchService {

    @GET("api/v2.1/locations")
    fun getLocation(
        @Header("user-key") userKey: String,
        @Query("query") query: String
    ): Call<LocationResult>
}