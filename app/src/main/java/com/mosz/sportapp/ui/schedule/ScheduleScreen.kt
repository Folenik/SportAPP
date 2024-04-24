package com.mosz.sportapp.ui.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.mosz.sportapp.ui.components.Error
import com.mosz.sportapp.ui.components.ProgressIndicator
import com.mosz.sportapp.ui.components.ScheduleList
import com.mosz.sportapp.ui.events.EventsState

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel) {
    when (val scheduleState = viewModel.schedule.collectAsState().value) {
        is ScheduleState.Loading -> ProgressIndicator(modifier = Modifier.alpha(1f))
        is ScheduleState.Success -> {
            ScheduleList(items = scheduleState.schedule)
        }
        is ScheduleState.Error -> {
            Error(errorMessage = scheduleState.toString()) { viewModel.getSchedule() }
        }
    }

    DisposableEffect(Unit) {
        viewModel.startRefreshing()
        onDispose {
            viewModel.cancelRefreshing()
        }
    }
}
