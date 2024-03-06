package dev.trip.airflight.flights.presentation

import dev.trip.airflight.domain.flights.model.AirlineModel
import dev.trip.airflight.domain.flights.model.ArrivalModel
import dev.trip.airflight.domain.flights.model.DepartureModel
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.model.FlightModel
import dev.trip.airflight.domain.flights.model.FlightsModel
import dev.trip.airflight.domain.flights.repository.FlightsRepository
import dev.trip.airflight.domain.flights.usecases.GetAirportUseCases
import dev.trip.airflight.domain.flights.usecases.GetFlightUseCases
import dev.trip.airflight.presentation.flights.FlightsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.base.MockitoException


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FlightsViewModelTest {

    @Mock
    private lateinit var repository: FlightsRepository

    private lateinit var flightUseCase: GetFlightUseCases

    private lateinit var getAirportUseCase: GetAirportUseCases

    private lateinit var viewModel: FlightsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())

        flightUseCase = GetFlightUseCases(repository)
        getAirportUseCase = GetAirportUseCases(repository)
        viewModel = FlightsViewModel(flightUseCase, getAirportUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getFlights with success`() = runTest(UnconfinedTestDispatcher()) {
        val input = FlightInputModel()
        val mockFlight = arrayListOf(
            FlightsModel(
                flightDate = "2022-03-05",
                flightStatus = "Scheduled",
                departure = DepartureModel(
                    airport = "DepartureAirport1",
                    timeZone = "UTC",
                    terminal = "Terminal1",
                    gate = "Gate1",
                    scheduledDate = "2022-03-05T12:00:00",
                    estimatedDate = "2022-03-05T12:15:00",
                    delay = 5,
                    actualDate = "2022-03-05T12:30:00"
                ),
                arrival = ArrivalModel(
                    airport = "ArrivalAirport1",
                    timeZone = "UTC",
                    terminal = "Terminal1",
                    gate = "Gate1",
                    baggage = "Baggage1",
                    scheduledDate = "2022-03-05T14:00:00",
                    estimatedDate = "2022-03-05T14:15:00",
                    delay = 5,
                    actualDate = "2022-03-05T14:30:00"
                ),
                airLine = AirlineModel("Airline1", "IATA1", "ICAO1"),
                flight = FlightModel("FlightNumber1", "IATA1", "ICAO1")
            ), FlightsModel(
                flightDate = "2022-03-06",
                flightStatus = "Scheduled",
                departure = DepartureModel(
                    airport = "DepartureAirport2",
                    timeZone = "UTC",
                    terminal = "Terminal2",
                    gate = "Gate2",
                    scheduledDate = "2022-03-06T12:00:00",
                    estimatedDate = "2022-03-06T12:15:00",
                    delay = 10,
                    actualDate = "2022-03-06T12:30:00"
                ),
                arrival = ArrivalModel(
                    airport = "ArrivalAirport2",
                    timeZone = "UTC",
                    terminal = "Terminal2",
                    gate = "Gate2",
                    baggage = "Baggage2",
                    scheduledDate = "2022-03-06T14:00:00",
                    estimatedDate = "2022-03-06T14:15:00",
                    delay = 10,
                    actualDate = "2022-03-06T14:30:00"
                ),
                airLine = AirlineModel("Airline2", "IATA2", "ICAO2"),
                flight = FlightModel("FlightNumber2", "IATA2", "ICAO2")
            ), FlightsModel(
                flightDate = "2022-03-07",
                flightStatus = "Scheduled",
                departure = DepartureModel(
                    airport = "DepartureAirport3",
                    timeZone = "UTC",
                    terminal = "Terminal3",
                    gate = "Gate3",
                    scheduledDate = "2022-03-07T12:00:00",
                    estimatedDate = "2022-03-07T12:15:00",
                    delay = 15,
                    actualDate = "2022-03-07T12:30:00"
                ),
                arrival = ArrivalModel(
                    airport = "ArrivalAirport3",
                    timeZone = "UTC",
                    terminal = "Terminal3",
                    gate = "Gate3",
                    baggage = "Baggage3",
                    scheduledDate = "2022-03-07T14:00:00",
                    estimatedDate = "2022-03-07T14:15:00",
                    delay = 15,
                    actualDate = "2022-03-07T14:30:00"
                ),
                airLine = AirlineModel("Airline3", "IATA3", "ICAO3"),
                flight = FlightModel("FlightNumber3", "IATA3", "ICAO3")
            )
        )
        `when`(repository.getFlights(input)).thenReturn(mockFlight)
        viewModel.getFlights(input)
        launch(UnconfinedTestDispatcher()) {
            delay(5000)
        }
        advanceUntilIdle()
        viewModel.flightFlow.value.apply {
            assertEquals(false, loading)
            assertEquals(mockFlight.size, flights?.size)
            assertNull(error)
        }

    }

    @Test
    fun `getFlights with failure`() = runTest(UnconfinedTestDispatcher()) {
        val input = FlightInputModel()
        `when`(repository.getFlights(input)).thenThrow(MockitoException("Server is down"))
        viewModel.getFlights(input)
        launch(UnconfinedTestDispatcher()) {
            delay(5000)
        }
        advanceUntilIdle()
        viewModel.flightFlow.value.apply {
            assertEquals(false, loading)
            assertNull(flights)
            assertNotNull(error)
            assertEquals("Server is down", error?.message)

        }
    }

    @Test
    fun `getAirports with failure`() = runTest(UnconfinedTestDispatcher()) {
        `when`(repository.getAirports()).thenThrow(MockitoException("Server is down"))
        viewModel.getAirports()
        launch(UnconfinedTestDispatcher()) {
            delay(5000)
        }
        advanceUntilIdle()
        viewModel.airportsFlow.value.apply {
            assertEquals(false, loading)
            assertNull(airports)
            assertNotNull(error)
            assertEquals("Server is down", error?.message)

        }
    }
}


