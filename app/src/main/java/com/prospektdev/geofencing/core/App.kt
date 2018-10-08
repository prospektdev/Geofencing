package com.prospektdev.geofencing.core

import android.app.Activity
import android.app.Application
import com.prospektdev.geofencing.core.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingActivityAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingActivityAndroidInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.initDagger(this)
    }
}