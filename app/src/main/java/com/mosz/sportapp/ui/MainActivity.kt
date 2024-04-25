package com.mosz.sportapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mosz.sportapp.utils.Screen
import com.mosz.sportapp.ui.events.EventsScreen
import com.mosz.sportapp.ui.events.EventsViewModel
import com.mosz.sportapp.ui.schedule.ScheduleScreen
import com.mosz.sportapp.ui.schedule.ScheduleViewModel
import com.mosz.sportapp.ui.video.VideoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val eventsViewModel: EventsViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val items = listOf(
        Screen.Events,
        Screen.Schedule
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    if (currentDestination?.route != Screen.VideoPlayer.route) {
                        NavigationBar {
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    modifier = Modifier.testTag("menuItem"),
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = Screen.Events.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Events.route) { EventsScreen(navController, eventsViewModel) }
                    composable(Screen.Schedule.route) { ScheduleScreen(scheduleViewModel) }
                    composable(Screen.VideoPlayer.route) { VideoScreen(navController) }
                }
            }
        }
    }
}