package com.prospektdev.geofencing.core.di

import com.prospektdev.geofencing.feature.MainActivity
import com.prospektdev.geofencing.feature.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    fun bindMainActivityInjector(): MainActivity
}
