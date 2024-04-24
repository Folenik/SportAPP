package com.mosz.sportapp.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosz.sportapp.data.Repository
import com.mosz.sportapp.utils.DispatcherProvider
import com.mosz.sportapp.utils.NetworkResult
import com.mosz.sportapp.utils.formatDate
import com.mosz.sportapp.utils.sortByDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private var _events = MutableStateFlow<EventsState>(EventsState.Loading)
    val events: StateFlow<EventsState> = _events.asStateFlow()

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch(dispatcherProvider.io()) {
            repository.getEvents().collect { values ->
                when (values) {
                    is NetworkResult.Success -> {
                        Timber.d("Fetch successful: ${values.data!!}")
                        _events.value = EventsState.Success(values.data
                            .sortByDate { it.date }
                            .map { it.copy(date = it.date.formatDate()) }
                        )
                    }

                    is NetworkResult.Error -> {
                        Timber.d("Fetch error: ${values.message}")
                        _events.value = EventsState.Error
                    }
                }
            }
        }
    }
}

