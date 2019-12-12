package com.aman.ecommerce

import android.app.Application
import com.aman.ecommerce.di.AppComponent
import com.aman.ecommerce.di.DaggerAppComponent

class EcomApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        const val TAG = "EcomApplication"

        private var appComponent: AppComponent? = null

        fun getAppComponent(): AppComponent? {
            return appComponent
        }
    }

}