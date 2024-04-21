package com.mosz.sportapp.data.model

import kotlinx.serialization.Serializable

typealias EventsResponse = List<Event>

@Serializable
data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: String,
    val imageUrl: String,
    val videoUrl: String?
)