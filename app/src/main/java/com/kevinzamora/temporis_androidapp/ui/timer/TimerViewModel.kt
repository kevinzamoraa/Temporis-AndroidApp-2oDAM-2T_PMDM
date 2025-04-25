package com.kevinzamora.temporis_androidapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kevinzamora.temporis_androidapp.model.Timer
import com.kevinzamora.temporis_androidapp.repository.TimerRepository

class TimerViewModel : ViewModel() {

    private val repository = TimerRepository()

    val timers: LiveData<List<Timer>> = repository.getTimers()

    fun addTimer(name: String, duration: Int, isActive: Boolean = false) {
        repository.addTimer(name, duration, isActive)
    }

    fun updateTimer(timer: Timer) {
        repository.updateTimer(timer)
    }

    fun deleteTimer(timerId: String) {
        repository.deleteTimer(timerId)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearListener()
    }
}
