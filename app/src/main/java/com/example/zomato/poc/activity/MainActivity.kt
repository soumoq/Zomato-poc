package com.example.zomato.poc.activity

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zomato.poc.R
import com.example.zomato.poc.model.restaurant.Restaurant
import com.example.zomato.poc.recyclerview.RestaurantAdapter
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

import com.example.zomato.poc.utility.*
import com.example.zomato.poc.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            getCurrentLatLon()
        } else {
            EasyPermissions.requestPermissions(this, "We need permissions to access location", 123, *permissions)
        }

        val restaurantAdapter = RestaurantAdapter(this)
        main_restaurant_list.adapter = restaurantAdapter

        main_search_bt.setOnClickListener(View.OnClickListener {
            if (main_search.text.toString().length > 2) {
                val searchViewModel: SearchViewModel =
                    ViewModelProvider(this).get(SearchViewModel::class.java)
                searchViewModel.locationInfo.observe(this, Observer {
                    if (it.locationSuggestions.size > 0) {
                        Utility.toast(
                            this,
                            " ${it.locationSuggestions.get(0).entityId} ${
                                it.locationSuggestions.get(
                                    0
                                ).entityType
                            }"
                        )

                        searchViewModel.searchInfo.observe(this, Observer {
                            if (it.restaurants.size > 0) {
                                restaurantAdapter.updateData(it.restaurants as ArrayList<Restaurant>)
                            }
                        })
                        searchViewModel.fetchSearch(
                            this,
                            it.locationSuggestions.get(0).entityId,
                            it.locationSuggestions.get(0).entityType,
                            5
                        )


                    } else {
                        Utility.toast(this, "No result found")
                    }
                })
                searchViewModel.fetchLocation(this, main_search.text.toString(), null, null)
            }
        })


    }

    private fun getCurrentLatLon() {
        val gpsTracker = GpsTracker(this)
        if (gpsTracker.canGetLocation()) {
            val latitude = gpsTracker.latitude
            val longitude = gpsTracker.latitude

            val searchViewModel: SearchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)
            searchViewModel.locationInfo.observe(this, Observer {
                if (it.locationSuggestions.size > 0) {
                    main_current_location.text = it.locationSuggestions[0].cityName
                } else {

                }
            })
            searchViewModel.fetchLocation(this, "", latitude, longitude)
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

}