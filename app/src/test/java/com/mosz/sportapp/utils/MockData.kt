package com.mosz.sportapp.utils

import com.mosz.sportapp.data.model.Event
import com.mosz.sportapp.data.model.EventsResponse
import com.mosz.sportapp.data.model.Schedule
import com.mosz.sportapp.data.model.SchedulesResponse

object MockData {
    private const val errorMessage = "Something went wrong.."
    private val singleEvent = Event(
        id = "1",
        title = "ManU vs ManCity",
        subtitle = "Premier League",
        date = "2024-04-25T01:46:40.493Z",
        imageUrl = "https://fastly.picsum.photos/id/907/200/300.jpg",
        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    )
    private val singleSchedule = Schedule(
        id = "1",
        title = "ManU vs ManCity",
        subtitle = "Premier League",
        date = "2024-04-25T01:46:40.493Z",
        imageUrl = "https://fastly.picsum.photos/id/907/200/300.jpg",
        videoUrl = ""
    )

    fun getEvents() = listOf(singleEvent, singleEvent)

    fun getEventsErrorResult() = NetworkResult.Error<EventsResponse>(errorMessage)

    fun getSchedule() = listOf(singleSchedule, singleSchedule)

    fun geScheduleErrorResult() = NetworkResult.Error<SchedulesResponse>(errorMessage)
}
