package com.mosz.sportapp.viewModelTests

import app.cash.turbine.test
import com.mosz.sportapp.data.Repository
import com.mosz.sportapp.ui.schedule.ScheduleState
import com.mosz.sportapp.ui.schedule.ScheduleViewModel
import com.mosz.sportapp.utils.MainDispatcherRule
import com.mosz.sportapp.utils.MockData
import com.mosz.sportapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ScheduleViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mock()
    private val viewModel by lazy {
        ScheduleViewModel(
            repository,
            mainDispatcherRule.testDispatcherProvider
        )
    }

    @Test
    fun `should try to get schedule when initialized and return success`() = runTest {
        whenever(repository.getSchedule()).thenReturn(
            flow {
                emit(NetworkResult.Success(MockData.getSchedule()))
            }
        )

        viewModel.schedule.test {
            assertEquals(ScheduleState.Success::class, awaitItem()::class)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should try to get schedule when initialized and return error`() = runTest {
        whenever(repository.getSchedule()).thenReturn(
            flow {
                emit(MockData.geScheduleErrorResult())
            }
        )

        viewModel.schedule.test {
            assertEquals(ScheduleState.Error::class, awaitItem()::class)
            cancelAndConsumeRemainingEvents()
        }
    }
}