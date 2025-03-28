package com.kevinzamora.temporis_androidapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import com.google.firebase.firestore.*
import com.google.firebase.ktx.firestore
import kotlinx.coroutines.flow.Result

class TimerRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    private val timersCollection = db.collection("timers")

    fun getTimers(): Flow<Result<List<Timer>>> = flow {
        try {
            val snapshot = timersCollection.get().await()
            val timers = snapshot.documents.map { doc ->
                doc.toObject(Timer::class.java)
            }
            emit(Result.success(timers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun createTimer(timer: Timer): Flow<Result<Boolean>> = flow {
        try {
            timersCollection.document(timer.id).set(timer).await()
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun updateTimer(timer: Timer): Flow<Result<Boolean>> = flow {
        try {
            timersCollection.document(timer.id).set(timer, SetOptions.merge()).await()
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}