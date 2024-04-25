package com.mosz.sportapp.viewModelTests

import app.cash.turbine.test
import com.mosz.sportapp.data.Repository
import com.mosz.sportapp.ui.events.EventsState
import com.mosz.sportapp.ui.events.EventsViewModel
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
class EventsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mock()
    private val viewModel by lazy {
        EventsViewModel(
            repository,
            mainDispatcherRule.testDispatcherProvider
        )
    }

    @Test
    fun `should try to get events when initialized and return success`() = runTest {
        whenever(repository.getEvents()).thenReturn(
            flow {
                emit(NetworkResult.Success(MockData.getEvents()))
            }
        )

        viewModel.events.test {
            assertEquals(EventsState.Success::class, awaitItem()::class)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should try to get events when initialized and return error`() = runTest {
        whenever(repository.getEvents()).thenReturn(
            flow {
                emit(MockData.getEventsErrorResult())
            }
        )

        viewModel.events.test {
            assertEquals(EventsState.Error::class, awaitItem()::class)
            cancelAndConsumeRemainingEvents()
        }
    }
}