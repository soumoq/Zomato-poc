package com.example.zomato.poc.activity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zomato.poc.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

import com.example.zomato.poc.utility.*
import com.example.zomato.poc.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            getCurrentLatLon()
            getLocBySearch()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "We need permissions to access location",
                123,
                *permissions
            )
        }


        main_search.addTextChangedListener {
            
        }

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


    private fun getLocBySearch() {
        val searchViewModel: SearchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.locationInfo.observe(this, Observer {
            if (it.locationSuggestions.size > 0) {
            } else {
            }
        })
        searchViewModel.fetchLocation(this, "Kolkata", null, null)
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

}