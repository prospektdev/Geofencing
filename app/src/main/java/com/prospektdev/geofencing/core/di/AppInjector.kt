package com.prospektdev.geofencing.core.di

import com.prospektdev.geofencing.core.App

object AppInjector {
    fun initDagger(app: App) {
        DaggerAppComponent.builder()
                .application(app)
                .build()
                .inject(app)
    }
}
