package com.prospektdev.geofencing.feature

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.prospektdev.geofencing.R


class GeofenceTransitionsIntentService : IntentService("ProspektGeofenceService") {

    override fun onHandleIntent(intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            return
        }
        val geofenceTransition = geofencingEvent.geofenceTransition
        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(this, R.string.user_enter__area, Toast.LENGTH_LONG).show()
                println("GeofenceTransitionsIntentService.onHandleIntent ---> triggered geofence !!!!!! requestId=${geofencingEvent.triggeringGeofences[0].requestId}")
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(this, R.string.user_leave_area, Toast.LENGTH_LONG).show()
                println("GeofenceTransitionsIntentService.onHandleIntent ---> exit from geofence requestId=${geofencingEvent.triggeringGeofences[0].requestId}")
            }
        }
    }

}