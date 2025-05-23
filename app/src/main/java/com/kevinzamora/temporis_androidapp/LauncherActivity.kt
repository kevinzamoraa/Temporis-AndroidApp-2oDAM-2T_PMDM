package com.kevinzamora.temporis_androidapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kevinzamora.temporis_androidapp.ui.auth.login.LoginActivity
import com.kevinzamora.temporis_androidapp.ui.home.HomeActivity
import com.kevinzamora.temporis_androidapp.ui.timer.TimerActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Comprobamos si el usuario ya está autenticado
        val currentUser = FirebaseAuth.getInstance().currentUser

        val nextActivity = if (currentUser != null) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(nextActivity)
        finish()
    }
}