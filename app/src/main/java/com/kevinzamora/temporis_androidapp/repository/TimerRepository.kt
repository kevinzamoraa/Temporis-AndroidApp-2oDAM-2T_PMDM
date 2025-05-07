package com.kevinzamora.temporis_androidapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.kevinzamora.temporis_androidapp.model.Timer

class TimerRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val timersLiveData = MutableLiveData<List<Timer>>()
    private var listenerRegistration: ListenerRegistration? = null

    fun getTimers(): LiveData<List<Timer>> {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            listenerRegistration?.remove() // Evitar duplicados
            listenerRegistration = firestore.collection("timers")
                .whereEqualTo("uid", userId) // Asegúrate de usar el campo correcto
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("TimerRepository", "Error al obtener temporizadores", error)
                        timersLiveData.value = emptyList()
                        return@addSnapshotListener
                    }

                    Log.d("TimerRepository", "Snapshot recibido: ${snapshot?.documents?.size} documentos")

                    val timers = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(Timer::class.java)?.apply { id = doc.id }
                    } ?: emptyList()

                    Log.d("TimerRepository", "Timers parseados: $timers")

                    timersLiveData.value = timers
                }
        } else {
            timersLiveData.value = emptyList()
        }
        return timersLiveData
    }


    fun addTimer(name: String, duration: Int, isActive: Boolean = false) {
        val uid = auth.currentUser?.uid ?: return

        val newTimer = hashMapOf(
            "name" to name,
            "duration" to duration,
            "isActive" to isActive,
            "createdAt" to Timestamp.now(),
            "uid" to uid
        )

        firestore.collection("timers")
            .add(newTimer)
            .addOnSuccessListener { Log.d("TimerRepository", "Temporizador añadido con ID: ${it.id}") }
            .addOnFailureListener { e -> Log.e("TimerRepository", "Error al añadir temporizador", e) }
    }

    fun updateTimer(timer: Timer) {
        if (timer.id.isNullOrEmpty()) return

        val updatedTimer = hashMapOf(
            "name" to timer.name,
            "duration" to timer.duration,
            "isActive" to timer.isActive,
            "createdAt" to timer.createdAt,
            "uid" to auth.currentUser?.uid
        )

        firestore.collection("timers").document(timer.id!!)
            .set(updatedTimer)
            .addOnSuccessListener { Log.d("TimerRepository", "Temporizador actualizado") }
            .addOnFailureListener { e -> Log.e("TimerRepository", "Error al actualizar temporizador", e) }
    }

    fun deleteTimer(timerId: String) {
        firestore.collection("timers").document(timerId)
            .delete()
            .addOnSuccessListener { Log.d("TimerRepository", "Temporizador eliminado") }
            .addOnFailureListener { e -> Log.e("TimerRepository", "Error al eliminar temporizador", e) }
    }

    fun clearListener() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }
}
