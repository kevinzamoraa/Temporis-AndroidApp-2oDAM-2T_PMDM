package com.kevinzamora.temporis_androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kevinzamora.temporis_androidapp.R
import com.kevinzamora.temporis_androidapp.ui.auth.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //dejamos 3 segundos para cargar el splash screen
        lifecycleScope.launch {
            delay(3000)

                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
        }
    }
}