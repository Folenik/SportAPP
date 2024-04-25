package com.mosz.sportapp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.mosz.sportapp.data.model.Schedule

@Composable
fun ScheduleList(items: List<Schedule>) {
    LazyColumn {
        items(items) { item ->
            SingleItem(
                image = item.imageUrl,
                title = item.title,
                subtitle = item.subtitle,
                date = item.date,
                onItemClick = { }
            )
        }
    }
}