package com.aman.ecommerce

import android.app.Application
import android.util.Log
import com.aman.ecommerce.di.AppComponent
import com.aman.ecommerce.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class EcomApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Log.d(TAG, " >>> Initializing Stetho")
        }

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