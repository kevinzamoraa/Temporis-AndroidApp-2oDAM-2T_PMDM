package com.kevinzamora.temporis_androidapp.model

import com.google.firebase.Timestamp

data class Timer(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val duration: Long = 0,
    val isActive: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
)