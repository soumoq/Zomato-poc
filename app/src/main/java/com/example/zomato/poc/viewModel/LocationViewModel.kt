package com.example.zomato.poc.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zomato.poc.model.location.LocationResult
import com.example.zomato.poc.retrofit.RestApiServiceBuilder
import com.example.zomato.poc.retrofit.SearchService
import com.example.zomato.poc.utility.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel : ViewModel() {
    val locationInfo: MutableLiveData<LocationResult> = MutableLiveData<LocationResult>()

    fun fetchLocation(context: Context, query: String) {
        getData(context, query)
    }

    private fun getData(context: Context, query: String) {
        var searchService = RestApiServiceBuilder.buildService(SearchService::class.java)
        val response: Call<LocationResult> =
            searchService.getLocation("1b3c8b37ea96785391fa55c288ac385c", query)
        response.enqueue(object : Callback<LocationResult> {
            override fun onResponse(
                call: Call<LocationResult>,
                response: Response<LocationResult>
            ) {
                locationInfo.value = response.body()
            }

            override fun onFailure(call: Call<LocationResult>, t: Throwable) {
                Utility.toast(context, "Something went wrong")

            }

        })
    }
}