package com.kevinzamora.temporis_androidapp.model

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val accessibleMode: Boolean = false,
    val timers: List<String> = emptyList()
)