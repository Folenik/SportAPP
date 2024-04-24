package com.mosz.sportapp.data

import com.mosz.sportapp.data.model.BaseResponse
import com.mosz.sportapp.data.model.EventsResponse
import com.mosz.sportapp.data.model.SchedulesResponse
import com.mosz.sportapp.data.remote.RemoteDataSource
import com.mosz.sportapp.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseResponse() {

    suspend fun getEvents(): Flow<NetworkResult<EventsResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getEvents() })
        }
    }

    suspend fun getSchedule(): Flow<NetworkResult<SchedulesResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getSchedule() })
        }
    }
}