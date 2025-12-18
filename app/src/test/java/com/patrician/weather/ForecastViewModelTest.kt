package com.patrician.weather

import app.cash.turbine.test
import com.patrician.weather.data.repository.ForecastRepository
import com.patrician.weather.ui.forecast.ForecastUiState
import com.patrician.weather.ui.forecast.ForecastViewModel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTest {

    private val repository: ForecastRepository = mockk()
    private lateinit var viewModel: ForecastViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ForecastViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadForecast should update state to Success when repository returns data`() = runTest {
        val mockForecasts = listOf(mockk<com.patrician.weather.data.local.entity.ForecastEntity>(relaxed = true))
        coEvery { repository.getForecastByCity("Tokyo") } returns mockForecasts
        viewModel.uiState.test {
            val initialState = awaitItem()
            assert(initialState !is ForecastUiState.Success)
            viewModel.loadForecast("Tokyo")
            val successState = awaitItem()
            assert(successState is ForecastUiState.Success)
            assertEquals(mockForecasts, (successState as ForecastUiState.Success).forecasts)
            expectNoEvents()
        }
    }

}
