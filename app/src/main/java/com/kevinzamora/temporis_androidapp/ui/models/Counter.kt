package com.kevinzamora.temporis_androidapp.ui.models

data class Counter( // Counter - System for tracking goals
    val id: String = "",
    val title: String = "",
    val type: String = "",
    val actualValue: Int = 0,
    val dailyObjective: Int = 0,
    val active: Boolean = true
) {
    fun reachedGoal(): Boolean {
        return actualValue >= dailyObjective
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "title" to title,
            "type" to type,
            "actualValue" to actualValue,
            "dailyObjective" to dailyObjective,
            "active" to active
        )
    }
}