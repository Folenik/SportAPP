package com.mosz.sportapp.ui.events

import com.mosz.sportapp.data.model.EventsResponse

sealed class EventsState {
    data object Loading : EventsState()
    data object Error : EventsState()
    data class Success(val events: EventsResponse) : EventsState()
}
