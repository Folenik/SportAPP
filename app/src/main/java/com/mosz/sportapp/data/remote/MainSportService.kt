package com.mosz.sportapp.data.remote

import com.mosz.sportapp.BuildConfig
import com.mosz.sportapp.data.model.Event
import com.mosz.sportapp.data.model.EventsResponse
import com.mosz.sportapp.data.model.Schedule
import com.mosz.sportapp.data.model.SchedulesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainSportService {
    @GET(BuildConfig.API_GET_EVENTS_ENDPOINT)
    suspend fun getEvents(): Response<EventsResponse>

    @GET(BuildConfig.API_GET_SCHEDULE_ENDPOINT)
    suspend fun getSchedule(): Response<SchedulesResponse>
}