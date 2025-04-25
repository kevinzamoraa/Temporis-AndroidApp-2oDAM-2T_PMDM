package com.kevinzamora.temporis_androidapp.ui.timer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinzamora.temporis_androidapp.databinding.ActivityTimerBinding
import com.kevinzamora.temporis_androidapp.model.Timer
import com.kevinzamora.temporis_androidapp.ui.timer.TimerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimerBinding
    private lateinit var adapter: TimerAdapter
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadTimers()
    }

    private fun setupRecyclerView() {
        adapter = TimerAdapter()

        // Definimos acciones para cada botón del temporizador
        adapter.onPlayClick = { timer ->
            Toast.makeText(this, "Iniciar: ${timer.name}", Toast.LENGTH_SHORT).show()
            // Aquí podrías lanzar un temporizador real con CountDownTimer o similar
        }

        adapter.onEditClick = { timer ->
            Toast.makeText(this, "Editar: ${timer.name}", Toast.LENGTH_SHORT).show()
            // Podrías abrir un diálogo o una nueva actividad para editarlo
        }

        adapter.onDeleteClick = { timer ->
            db.collection("timers").document(timer.id!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Temporizador eliminado", Toast.LENGTH_SHORT).show()
                    loadTimers()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
        }

        binding.recyclerViewTimers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTimers.adapter = adapter
    }

    private fun loadTimers() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("timers")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val timers = result.mapNotNull { it.toObject(Timer::class.java).apply { id = it.id } }
                adapter.submitList(timers)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar temporizadores", Toast.LENGTH_SHORT).show()
            }
    }
}