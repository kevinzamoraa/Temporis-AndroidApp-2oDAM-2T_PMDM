package com.kevinzamora.temporis_androidapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
// import com.kevinzamora.temporis_androidapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    /*private lateinit var binding: ActivityHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Mis contadores"

        // Aquí se mostrarán los contadores (más adelante lo conectamos a RecyclerView)

        // Botón para crear un nuevo contador
        binding.btnCrearContador.setOnClickListener {
            val intent = Intent(this, CrearContadorActivity::class.java)
            startActivity(intent)
        }
    }*/
}