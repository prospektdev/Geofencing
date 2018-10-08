package com.prospektdev.geofencing.model.location

import android.Manifest
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat


object CheckPermission {

    //  CHECK FOR LOCATION PERMISSION
    fun checkPermission(activity: Activity): Boolean {
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    //REQUEST FOR PERMISSSION
    fun requestPermission(activity: Activity, code: Int) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            Toast.makeText(activity, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()

        } else {

            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), code)
        }
    }

}