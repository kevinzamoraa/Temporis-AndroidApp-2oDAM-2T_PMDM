package com.kevinzamora.temporis_androidapp.model

data class TimeRegister( // TimeRegister - For tracking time and activities
    val id: String = "",
    val date: Long = 0L,
    val duration: Int = 0,
    val category: String = "",
    val description: String = "",
    val counterId: String? = null
) {
    fun isValid(): Boolean {
        return id.isNotEmpty() &&
                date > 0L &&
                duration >= 0
    }

    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["id"] = id
        map["date"] = date
        map["duration"] = duration
        map["category"] = category
        map["description"] = description

        // Null value especial managing
        counterId?.let { map["counterId"] = it }

        return map
    }
}