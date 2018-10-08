package com.prospektdev.geofencing.feature

import dagger.Binds
import dagger.Module

@Module
interface MainModule {
    @Binds
    fun provideView(activity: MapsActivity): MapsActivity
}