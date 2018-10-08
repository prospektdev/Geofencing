package com.prospektdev.geofencing.core.di

import android.arch.lifecycle.ViewModel
import com.prospektdev.geofencing.feature.MainViewModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModule::class)
    fun bindLoginViewModel(viewModel: MainViewModule): ViewModel
}