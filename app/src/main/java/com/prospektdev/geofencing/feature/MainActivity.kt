package com.prospektdev.geofencing.feature

import com.prospektdev.geofencing.R
import com.prospektdev.geofencing.core.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: MainViewModule
    override fun obtainLayoutResId(): Int {
        return R.layout.activity_main
    }
}