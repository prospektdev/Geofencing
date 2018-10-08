package com.prospektdev.geofencing.core.di

import android.app.Application
import android.content.Context
import com.prospektdev.geofencing.model.location.LocationManager
import com.prospektdev.geofencing.model.location.LocationSource
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {
    @Singleton
    @Binds
    fun provideContext(app: Application): Context

    @Singleton
    @Binds
    fun provideLocation(location: LocationManager): LocationSource
}
