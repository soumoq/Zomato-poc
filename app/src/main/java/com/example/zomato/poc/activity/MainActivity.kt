package com.example.zomato.poc.activity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zomato.poc.R
import com.example.zomato.poc.model.location.LocationResult
import com.example.zomato.poc.retrofit.RestApiServiceBuilder
import com.example.zomato.poc.retrofit.SearchService
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

import com.example.zomato.poc.utility.*
import com.example.zomato.poc.viewModel.LocationViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            //getCurrentLatLon()
            getLocBySearch()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "We need permissions to access location",
                123,
                *permissions
            );
        }

    }

    private fun getCurrentLatLon() {
        val gpsTracker = GpsTracker(this)
        if (gpsTracker.canGetLocation()) {
            val latitude = gpsTracker.latitude
            val longitude = gpsTracker.latitude
        }
    }

    private fun getLocBySearch() {
        val locationViewModel: LocationViewModel =
            ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.locationInfo.observe(this, Observer {
            if (it.locationSuggestions.size > 0) {
                Utility.toast(this, ">1")
            } else {
                Utility.toast(this, "<1")
            }
        })
        locationViewModel.fetchLocation(this, "Kolkata")
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

}