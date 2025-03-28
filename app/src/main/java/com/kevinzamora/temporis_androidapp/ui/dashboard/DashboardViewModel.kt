package com.kevinzamora.temporis_androidapp.ui.dashboard

import android.app.usage.UsageEvents
import androidx.lifecycle.*
import com.kevinzamora.temporis_androidapp.model.Timer
import com.kevinzamora.temporis_androidapp.repository.TimerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class DashboardViewModel(private val repository: TimerRepository) : ViewModel() {
    private val _timers = MutableLiveData<List<Timer>>()
    val timers: LiveData<List<Timer>> = _timers

    private val _navigationEvent = MutableLiveData<UsageEvents.Event<Navigation>>()
    val navigationEvent: LiveData<Event<Navigation>> = _navigationEvent

    fun getTimers() {
        viewModelScope.launch {
            repository.getTimers().collect { result ->
                when (result) {
                    is Result.Success -> _timers.value = result.data
                    is Result.Failure -> Log.e("DashboardVM", "Error: ${result.exception.message}")
                }
            }
        }
    }

    fun onCreateTimerClick() {
        _navigationEvent.value = Event(Navigation.CreateTimer)
    }

    fun onTimerClick(timer: Timer) {
        _navigationEvent.value = Event(Navigation.EditTimer(timer))
    }

    sealed class Navigation {
        object CreateTimer : Navigation()
        data class EditTimer(val timer: Timer) : Navigation()
    }
}