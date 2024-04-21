package com.mosz.sportapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosz.sportapp.data.Repository
import com.mosz.sportapp.utils.DispatcherProvider
import com.mosz.sportapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private var _events = MutableStateFlow<EventsState>(EventsState.Loading)
    val events: StateFlow<EventsState> = _events.asStateFlow()

    private var _schedule = MutableStateFlow<ScheduleState>(ScheduleState.Loading)
    val schedule: StateFlow<ScheduleState> = _schedule.asStateFlow()

    init {
        getEvents()
        getSchedule()
    }

    fun init() {
        Timber.d("TestViewModel initialized.")
    }

    private fun getEvents() {
        viewModelScope.launch(dispatcherProvider.io()) {
            repository.getEvents().collect { values ->
                when (values) {
                    is NetworkResult.Success -> {
                        Timber.d("Fetch successful: ${values.data!!}")
                        _events.value = EventsState.Success(values.data)
                    }

                    is NetworkResult.Error -> {
                        Timber.d("Fetch error: ${values.message}")
                        _events.value = EventsState.Error
                    }
                }
            }
        }
    }

    private fun getSchedule() {
        viewModelScope.launch(dispatcherProvider.io()) {
            repository.getSchedule().collect { values ->
                when (values) {
                    is NetworkResult.Success -> {
                        Timber.d("Fetch successful: ${values.data!!}")
                        _schedule.value = ScheduleState.Success(values.data)
                    }

                    is NetworkResult.Error -> {
                        Timber.d("Fetch error: ${values.message}")
                        _schedule.value = ScheduleState.Error
                    }
                }
            }
        }
    }
}

