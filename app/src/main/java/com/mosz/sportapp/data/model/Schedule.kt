package com.mosz.sportapp.data.model

import kotlinx.serialization.Serializable

typealias SchedulesResponse = List<Schedule>

@Serializable
data class Schedule(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: String,
    val imageUrl: String,
    val videoUrl: String? = null
)