package com.mosz.sportapp.ui.components

import android.os.Bundle
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mosz.sportapp.data.model.Event
import com.mosz.sportapp.utils.navigate

@Composable
fun EventsList(items: List<Event>, navController: NavController) {
    LazyColumn {
        items(items) { item ->
            SingleItem(
                image = item.imageUrl,
                title = item.title,
                subtitle = item.subtitle,
                date = item.date,
                onItemClick = {
                    val bundle = Bundle().apply {
                        putString("videoUrl", item.videoUrl)
                    }
                    navController.navigate(Screen.VideoPlayer.route, bundle)
                }
            )
        }
    }
}