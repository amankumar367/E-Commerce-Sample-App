package com.aman.ecommerce.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.aman.ecommerce.R
import com.aman.ecommerce.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            HomeActivity.start(this)
            finish()
        }, 2000)

    }
}
