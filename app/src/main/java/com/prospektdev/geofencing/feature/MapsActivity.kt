package com.prospektdev.geofencing.feature

import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.prospektdev.geofencing.R
import com.prospektdev.geofencing.core.base.BaseActivity
import com.prospektdev.geofencing.model.location.CheckPermission
import com.prospektdev.geofencing.util.Constants
import javax.inject.Inject


class MapsActivity : BaseActivity(), OnMapReadyCallback {
    @Inject
    lateinit var viewModel: MainViewModule
    private val geofencingClient by lazy { LocationServices.getGeofencingClient(this) }

    private val btnFindMe: Button by lazy { findViewById<Button>(R.id.btnFindMe) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun obtainLayoutResId(): Int = R.layout.activity_maps

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.userLocation.observe(this, Observer {
            if (it != null) {
                val currentPosition = LatLng(it.latitude, it.longitude)
                println("MapsActivity.onMapReady ---> lat=${it.latitude} lon=${it.longitude}")
                googleMap.addMarker(MarkerOptions().position(currentPosition).title("It's Me"))
                val cameraPosition = CameraPosition.Builder()
                        .target(currentPosition)
                        .zoom(17f)
                        .build()
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        })
        btnFindMe.setOnClickListener {
            viewModel.getLastKnownLocation()
        }

        googleMap.setOnMapClickListener { it ->
            it.let {
                addCircle(googleMap, it)
                addGeofence(it)
            }
        }
    }

    private fun addCircle(googleMap: GoogleMap, latLon: LatLng) {
        googleMap.addCircle(CircleOptions()
                .radius(Constants.GEOFENCE_RADIUS_IN_METERS.toDouble())
                .center(latLon)
                .strokeWidth(5f)
                .fillColor(254))
    }

    private fun addGeofence(it: LatLng) {
        val geofence = Geofence.Builder()
                .setRequestId("UserGeofenceLat${it.latitude}Lon${it.longitude}")
                .setCircularRegion(
                        it.latitude,
                        it.longitude,
                        Constants.GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
        if (!CheckPermission.checkPermission(this)) {
            CheckPermission.requestPermission(this, 256)
        }
        geofencingClient.addGeofences(
                GeofencingRequest.Builder().apply {
                    setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER and GeofencingRequest.INITIAL_TRIGGER_EXIT)
                    addGeofence(geofence)
                }.build(), geofencePendingIntent)
                .addOnSuccessListener { println("MapsActivity.addGeofence ---> geofence added successfully") }
                .addOnCanceledListener { println("MapsActivity.addGeofence ---> geofence added with error") }
    }
}
