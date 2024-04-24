package com.mosz.sportapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.mosz.sportapp.R

sealed class Screen(val route: String, val resourceId: Int, val icon: ImageVector) {
    data object Events : Screen("events", R.string.navigation_events, Icons.Filled.Star)
    data object Schedule : Screen("schedule", R.string.navigation_schedule, Icons.Filled.DateRange)
    data object VideoPlayer : Screen("videoPlayer", R.string.navigation_events, Icons.Filled.PlayArrow)
}