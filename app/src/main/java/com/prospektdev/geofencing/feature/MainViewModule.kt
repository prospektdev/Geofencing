package com.prospektdev.geofencing.feature

import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.prospektdev.geofencing.core.base.BaseViewModel
import com.prospektdev.geofencing.model.location.LocationSource
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModule @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var locationManager: LocationSource
    var userLocation: MutableLiveData<Location> = MutableLiveData()

    fun getLastKnownLocation(){
       locationManager.getLastKnownLocation()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({location-> userLocation.value = location}) {}
    }
}