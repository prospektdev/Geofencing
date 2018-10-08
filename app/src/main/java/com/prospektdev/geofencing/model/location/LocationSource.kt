package com.prospektdev.geofencing.model.location

import android.location.Location
import io.reactivex.Single

interface LocationSource {
    fun getLastKnownLocation(): Single<Location>
}

class UnknownLocationException : Exception()