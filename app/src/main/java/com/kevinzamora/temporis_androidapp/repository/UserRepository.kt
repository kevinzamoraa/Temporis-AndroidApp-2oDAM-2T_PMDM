package com.kevinzamora.temporis_androidapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kevinzamora.temporis_androidapp.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    private val usersCollection = db.collection("users")

    fun getUser(userId: String): Flow<Result<User>> = flow {
        try {
            val snapshot = usersCollection.document(userId).get().await()
            val user = snapshot.toObject(User::class.java)
            emit(Result.success(user))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private fun emit(value: Result<User?>) {

    }

    fun createUser(user: User): Flow<Result<Boolean>> = flow {
        try {
            usersCollection.document(user.uid).set(user).await()
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun updateUser(user: User): Flow<Result<Boolean>> = flow {
        try {
            usersCollection.document(user.uid).set(user, SetOptions.merge()).await()
            emit(Result.success(true))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}