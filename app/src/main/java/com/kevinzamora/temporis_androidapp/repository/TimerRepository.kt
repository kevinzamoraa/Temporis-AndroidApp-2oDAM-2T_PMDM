package com.kevinzamora.temporis_androidapp.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import com.kevinzamora.temporis_androidapp.model.Timer

class TimerRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val timersCollection = db.collection("users").document(userId).collection("timers")

    fun createTimer(name: String, duration: Int, onSuccess: () -> Unit) {
        val timer = hashMapOf(
            "name" to name,
            "duration" to duration,
            "isActive" to false,
            "createdAt" to FieldValue.serverTimestamp()
        )
        timersCollection.add(timer).addOnSuccessListener { onSuccess() }
    }

    fun getTimers(onComplete: (List<Timer>) -> Unit) {
        timersCollection.get().addOnSuccessListener { snapshot ->
            val timers = snapshot.documents.map { doc ->
                Timer(
                    doc.id,
                    doc.getString("name") ?: "",
                    doc.getLong("duration") ?: 0,
                    doc.getBoolean("isActive") ?: false,
                    doc.getTimestamp("createdAt") ?: Timestamp.now()
                )
            }
            onComplete(timers)
        }
    }

    fun updateTimer(timer: Timer): Flow<Result<Boolean>> = flow {
        try {
            timersCollection.document(timer.id).set(timer).await()
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}