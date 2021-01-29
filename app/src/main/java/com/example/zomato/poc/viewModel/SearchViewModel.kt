package com.example.zomato.poc.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zomato.poc.model.location.LocationResult
import com.example.zomato.poc.model.restaurant.SearchRestaurant
import com.example.zomato.poc.retrofit.RestApiServiceBuilder
import com.example.zomato.poc.retrofit.SearchService
import com.example.zomato.poc.utility.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    val locationInfo: MutableLiveData<LocationResult> = MutableLiveData<LocationResult>()
    val searchInfo: MutableLiveData<SearchRestaurant> = MutableLiveData<SearchRestaurant>()

    fun fetchLocation(context: Context, query: String, lat: Double?, lon: Double?) {
        getData(context, query, lat, lon)
    }

    fun fetchSearch(context: Context, entityId: Int, entity_type: String, count: Int?) {
        val searchService = RestApiServiceBuilder.buildService(SearchService::class.java)
        val response: Call<SearchRestaurant> =
            searchService.getRestore(
                "1b3c8b37ea96785391fa55c288ac385c", entityId, entity_type, count
            )
        response.enqueue(object : Callback<SearchRestaurant> {
            override fun onResponse(
                call: Call<SearchRestaurant>,
                response: Response<SearchRestaurant>
            ) {
                searchInfo.value = response.body()
            }

            override fun onFailure(call: Call<SearchRestaurant>, t: Throwable) {
                Utility.toast(context, "Something went wrong")
            }

        })
    }

    private fun getData(context: Context, query: String, lat: Double?, lon: Double?) {
        val searchService = RestApiServiceBuilder.buildService(SearchService::class.java)
        val response: Call<LocationResult> =
            searchService.getLocation("1b3c8b37ea96785391fa55c288ac385c", query, lat, lon, 1)
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