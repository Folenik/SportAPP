package com.mosz.sportapp.ui.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.mosz.sportapp.ui.components.ProgressIndicator
import com.mosz.sportapp.ui.components.ScheduleList

@Composable
fun ScheduleScreen(navController: NavController, viewModel: ScheduleViewModel) {
    when (val scheduleState = viewModel.schedule.collectAsState().value) {
        is ScheduleState.Loading -> ProgressIndicator(modifier = Modifier.alpha(1f))
        is ScheduleState.Success -> {
            ScheduleList(items = scheduleState.schedule)
        }
        is ScheduleState.Error -> {
            println("MLOGI scheduleStateError: $scheduleState")
        }
    }

    DisposableEffect(Unit) {
        viewModel.startRefreshing()
        onDispose {
            viewModel.cancelRefreshing()
        }
    }
}
