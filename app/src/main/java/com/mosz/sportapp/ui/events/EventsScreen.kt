package com.mosz.sportapp.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.mosz.sportapp.ui.components.EventsList
import com.mosz.sportapp.ui.components.ProgressIndicator

@Composable
fun EventsScreen(navController: NavController, viewModel: EventsViewModel) {
    when (val eventsState = viewModel.events.collectAsState().value) {
        is EventsState.Loading -> ProgressIndicator(modifier = Modifier.alpha(1f))
        is EventsState.Success -> {
            EventsList(items = eventsState.events, navController)
        }
        is EventsState.Error -> {
            println("MLOGI eventsStateError: $eventsState")
        }
    }
}