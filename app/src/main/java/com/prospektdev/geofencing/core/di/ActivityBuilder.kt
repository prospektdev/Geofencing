package com.prospektdev.geofencing.core.di

import com.prospektdev.geofencing.feature.MainModule
import com.prospektdev.geofencing.feature.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    fun bindMainActivityInjector(): MapsActivity
}
