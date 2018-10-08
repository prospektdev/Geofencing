package com.prospektdev.geofencing.feature

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.prospektdev.geofencing.R
import com.prospektdev.geofencing.core.base.BaseActivity
import com.prospektdev.geofencing.model.location.CheckPermission
import com.prospektdev.geofencing.model.location.LocationManager
import javax.inject.Inject


class MapsActivity : BaseActivity(), OnMapReadyCallback {
    @Inject
    lateinit var viewModel: MainViewModule

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var currentPosition: LatLng
    private lateinit var circle :Circle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = LocationManager(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (!CheckPermission.checkPermission(this)) {
            CheckPermission.requestPermission(this, 256)
        }
    }

    override fun obtainLayoutResId(): Int = R.layout.activity_maps

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewModel.userLocation.observe(this, Observer {
            if (it != null) {
                currentPosition = LatLng(it.latitude, it.longitude)
                Log.d("Location", "latitude - " + it.latitude + " long -" + it.longitude)
                mMap.addMarker(MarkerOptions().position(currentPosition).title("It's Me"))
                val cameraPosition = CameraPosition.Builder()
                        .target(currentPosition)
                        .zoom(17f)
                        .bearing(90f)
                        .tilt(30f)
                        .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        })
        viewModel.getLastKnownLocation()

        mMap.setOnMapClickListener {
             circle = mMap.addCircle(CircleOptions()
                    .radius(60.0)
                    .center(it)
                    .strokeWidth(2f)
                    .fillColor(254))

        }
    }
}
