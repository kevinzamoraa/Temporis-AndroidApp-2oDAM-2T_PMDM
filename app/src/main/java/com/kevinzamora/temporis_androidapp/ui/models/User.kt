package com.kevinzamora.temporis_androidapp.ui.models

data class User( // User - Main entity for system users
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val accessibleMode: Boolean = false
) {
    fun isValid(): Boolean {
        return uid.isNotEmpty() &&
                name.isNotEmpty() &&
                email.isNotEmpty()
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "accessibleMode" to accessibleMode
        )
    }
}