package com.mosz.sportapp.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val mainSportService: MainSportService) {
    suspend fun getEvents() =
        mainSportService.getEvents()

    suspend fun getSchedule() =
        mainSportService.getSchedule()
}