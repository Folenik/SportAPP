package com.mosz.sportapp.ui

import com.mosz.sportapp.data.model.SchedulesResponse

sealed class ScheduleState {
    data object Loading : ScheduleState()
    data object Error : ScheduleState()
    data class Success(val schedule: SchedulesResponse) : ScheduleState()
}
