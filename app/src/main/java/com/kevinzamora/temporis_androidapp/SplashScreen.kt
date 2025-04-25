package com.kevinzamora.temporis_androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.ads.mediationtestsuite.activities.HomeActivity
import com.kevinzamora.temporis_androidapp.R
import com.kevinzamora.temporis_androidapp.ui.auth.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mostramos una vista de splash si tienes XML asignado (opcional)
        setContentView(R.layout.activity_splash_screen)

        // Esperamos unos milisegundos (opcional) y luego redirigimos
        checkAuthState()
    }

    private fun checkAuthState() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            // Usuario autenticado → vamos a HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            // Usuario NO autenticado → vamos a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        finish() // Cerramos esta pantalla para que no se vuelva a mostrar al hacer back
    }
}