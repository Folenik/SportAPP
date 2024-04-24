package com.mosz.sportapp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosz.sportapp.data.Repository
import com.mosz.sportapp.utils.DispatcherProvider
import com.mosz.sportapp.utils.NetworkResult
import com.mosz.sportapp.utils.formatDate
import com.mosz.sportapp.utils.sortByDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private var _schedule = MutableStateFlow<ScheduleState>(ScheduleState.Loading)
    val schedule: StateFlow<ScheduleState> = _schedule.asStateFlow()
    private var refreshJob: Job? = null

    init {
        getSchedule()
    }

    fun startRefreshing() {
        refreshJob = viewModelScope.launch {
            while (true) {
                delay(30_000)
                getSchedule()
            }
        }
    }

    fun cancelRefreshing() {
        refreshJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }

    fun getSchedule() {
        viewModelScope.launch(dispatcherProvider.io()) {
            repository.getSchedule().collect { values ->
                when (values) {
                    is NetworkResult.Success -> {
                        Timber.d("Fetch successful: ${values.data!!}")
                        _schedule.value = ScheduleState.Success(
                            values.data
                                .sortByDate { it.date }
                                .map { it.copy(date = it.date.formatDate()) }
                        )
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

