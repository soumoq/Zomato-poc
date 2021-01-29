package com.example.zomato.poc.activity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zomato.poc.R
import com.example.zomato.poc.model.location.LocationResult
import com.example.zomato.poc.retrofit.RestApiServiceBuilder
import com.example.zomato.poc.retrofit.SearchService
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

import com.example.zomato.poc.utility.*
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
        var searchService = RestApiServiceBuilder.buildService(SearchService::class.java)
        val response: Call<LocationResult> =
            searchService.getLocation("1b3c8b37ea96785391fa55c288ac385c", "kolkata")
        response.enqueue(object : Callback<LocationResult> {
            override fun onResponse(
                call: Call<LocationResult>,
                response: Response<LocationResult>
            ) {
                if (response.isSuccessful) {
                    Utility.toast(this@MainActivity, "Success")
                } else {
                    Utility.toast(this@MainActivity, "Failed")
                }
            }

            override fun onFailure(call: Call<LocationResult>, t: Throwable) {
                Utility.toast(this@MainActivity, "Something went wrong")

            }

        })
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