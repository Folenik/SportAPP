package com.mosz.sportapp.utils

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun <T> List<T>.sortByDate(dateSelector: (T) -> String): List<T> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    return this.sortedBy { dateFormat.parse(dateSelector(it)) }
}

fun String.formatDate(): String {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val dailyDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val hourlyDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val date = dateTimeFormat.parse(this) ?: return ""

    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance()
    val tomorrow = Calendar.getInstance()
    yesterday.add(Calendar.DATE, -1)
    tomorrow.add(Calendar.DATE, 1)

    if (dailyDateFormat.format(today.time) == dailyDateFormat.format(date)) {
        return "Today, ${hourlyDateFormat.format(date)}"
    }

    if (dailyDateFormat.format(yesterday.time) == dailyDateFormat.format(date)) {
        return "Yesterday, ${hourlyDateFormat.format(date)}"
    }

    if (dailyDateFormat.format(tomorrow.time) == dailyDateFormat.format(date)) {
        return "Tomorrow, ${hourlyDateFormat.format(date)}"
    }

    return dailyDateFormat.format(date)
}

@SuppressLint("RestrictedApi")
fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}