package dev.trip.airflight.flights.domain

import dev.trip.airflight.data.flights.datasoruce.FlightRemoteDatasource
import dev.trip.airflight.data.flights.entity.AircraftInfoEntity
import dev.trip.airflight.data.flights.entity.AirlineInfoEntity
import dev.trip.airflight.data.flights.entity.AirportsDataEntity
import dev.trip.airflight.data.flights.entity.ArrivalEntity
import dev.trip.airflight.data.flights.entity.DepartureEntity
import dev.trip.airflight.data.flights.entity.FlightDataEntity
import dev.trip.airflight.data.flights.entity.FlightInfoEntity
import dev.trip.airflight.data.flights.entity.LiveInfoEntity
import dev.trip.airflight.data.flights.repository.FlightsRepositoryImpl
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.repository.FlightsRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class FlightsRepositoryTest {

    @Mock
    private lateinit var mockRemote: FlightRemoteDatasource


   private lateinit var repository: FlightsRepository


    @Before
    fun setup(){
        repository =  FlightsRepositoryImpl(mockRemote)
    }

    @Test
    fun `getFlights with success`() = runBlocking {
        val mockFlightsList = arrayListOf(
            FlightDataEntity(
                flightDate = "2019-12-13",
                flightStatus = "scheduled",
                price = 1584,
                currency = "USD",
                departure = DepartureEntity(
                    airport = "Los Angeles International",
                    timezone = "America/Los_Angeles",
                    iata = "LAX",
                    terminal = "2",
                    gate = "D1",
                    delay = 2,
                    scheduled = "2019-12-12T04:21:00+00:00",
                    estimated = null,
                    actual = null,
                    estimatedRunway = null,
                    actualRunway = null
                ),
                arrival = ArrivalEntity(
                    airport = "John F. Kennedy International",
                    timezone = "America/New_York",
                    iata = "JFK",
                    terminal = "B",
                    gate = "A1",
                    baggage = null,
                    delay = 1,
                    scheduled = "2019-12-12T08:31:00+00:00",
                    estimated = "2019-12-12T08:31:00+00:00",
                    actual = "2019-12-12T08:31:00+00:00",
                    estimatedRunway = null,
                    actualRunway = null
                ),
                airline = AirlineInfoEntity(
                    name = "Delta Air Lines",
                    iata = "DL",
                    icao = null
                ),
                flight = FlightInfoEntity(
                    number = "1001",
                    iata = "DL1001",
                    icao = null,
                    codeshared = null
                ),
                aircraft = AircraftInfoEntity(
                    registration = "N160A1",
                    iata = "B737",
                    icao = "XYZ",
                    icao24 = "XYZ123"
                ),
                live = LiveInfoEntity(
                    updated = "2022-03-05T10:00:00",
                    latitude = 34.0522,
                    longitude = -118.2437,
                    altitude = 35000.0,
                    direction = 120.0,
                    speedHorizontal = 500.0,
                    speedVertical = 0.0,
                    isGround = false
                )
            ),

            FlightDataEntity(
                flightDate = "2019-12-12",
                flightStatus = "active",
                price = 1776,
                currency = "USD",
                departure = DepartureEntity(
                    airport = "San Francisco International",
                    timezone = "America/Los_Angeles",
                    iata = "SFO",
                    terminal = "3",
                    gate = "D2",
                    delay = 4,
                    scheduled = "2019-12-12T04:22:00+00:00",
                    estimated = null,
                    actual = null,
                    estimatedRunway = null,
                    actualRunway = null
                ),
                arrival = ArrivalEntity(
                    airport = "Dallas/Fort Worth International",
                    timezone = "America/Chicago",
                    iata = "DFW",
                    terminal = "A",
                    gate = "A2",
                    baggage = null,
                    delay = 2,
                    scheduled = "2019-12-12T08:32:00+00:00",
                    estimated = "2019-12-12T08:32:00+00:00",
                    actual = "2019-12-12T08:32:00+00:00",
                    estimatedRunway = null,
                    actualRunway = null
                ),
                airline = AirlineInfoEntity(
                    name = "American Airlines",
                    iata = "AA",
                    icao = null
                ),
                flight = FlightInfoEntity(
                    number = "1002",
                    iata = "AA1002",
                    icao = null,
                    codeshared = null
                ),
                aircraft = AircraftInfoEntity(
                    registration = "N160A2",
                    iata = "A321",
                    icao = "ABC",
                    icao24 = "ABC123"
                ),
                live = LiveInfoEntity(
                    updated = "2022-03-05T10:15:00",
                    latitude = 37.7749,
                    longitude = -122.4194,
                    altitude = 33000.0,
                    direction = 90.0,
                    speedHorizontal = 550.0,
                    speedVertical = 0.0,
                    isGround = false
                )
            ),

            FlightDataEntity(
                flightDate = "2019-12-13",
                flightStatus = "scheduled",
                price = 9848,
                currency = "USD",
                departure = DepartureEntity(
                    airport = "Los Angeles International",
                    timezone = "America/Los_Angeles",
                    iata = "LAX",
                    terminal = "4",
                    gate = "D3",
                    delay = 6,
                    scheduled = "2019-12-12T04:23:00+00:00",
                    estimated = null,
                    actual = null,
                    estimatedRunway = null,
                    actualRunway = null
                ),
                arrival = ArrivalEntity(
                    airport = "John F. Kennedy International",
                    timezone = "America/New_York",
                    iata = "JFK",
                    terminal = "B",
                    gate = "A3",
                    baggage = null,
                    delay = 3,
                    scheduled = "2019-12-12T08:33:00+00:00",
                    estimated = "2019-12-12T08:33:00+00:00",
                    actual = "2019-12-12T08:33:00+00:00",
                    estimatedRunway = null,
                    actualRunway = null
                ),
                airline = AirlineInfoEntity(
                    name = "Delta Air Lines",
                    iata = "DL",
                    icao = null
                ),
                flight = FlightInfoEntity(
                    number = "1003",
                    iata = "DL1003",
                    icao = null,
                    codeshared = null
                ),
                aircraft = AircraftInfoEntity(
                    registration = "N160A3",
                    iata = "B737",
                    icao = "XYZ",
                    icao24 = "XYZ123"
                ),
                live = LiveInfoEntity(
                    updated = "2022-03-05T10:30:00",
                    latitude = 40.6413,
                    longitude = -73.7781,
                    altitude = 36000.0,
                    direction = 150.0,
                    speedHorizontal = 600.0,
                    speedVertical = 0.0,
                    isGround = false
                )
            ),
        )
        `when`(mockRemote.getFlights()).thenReturn(mockFlightsList)
        val result = repository.getFlights(FlightInputModel())
        assertNotNull(result)
        assertEquals(3, result!!.size)
    }

    @Test
    fun `getFlights with empty data`() = runBlocking {
        `when`(mockRemote.getFlights()).thenReturn(arrayListOf())
        val result = repository.getFlights(FlightInputModel())
        assertNotNull(result)
        assertTrue(result!!.isEmpty())
    }

    @Test
    fun `getFlights with sorting`() = runBlocking {
        val mockFlightsList = arrayListOf(
            FlightDataEntity(
                flightDate = "2019-12-12",
                flightStatus = "active",
                price = 1776,
                currency = "USD",
                departure = DepartureEntity(
                    airport = "San Francisco International",
                    timezone = "America/Los_Angeles",
                    iata = "SFO",
                    terminal = "3",
                    gate = "D2",
                    delay = 4,
                    scheduled = "2019-12-12T04:22:00+00:00",
                    estimated = null,
                    actual = null,
                    estimatedRunway = null,
                    actualRunway = null
                ),
                arrival = ArrivalEntity(
                    airport = "Dallas/Fort Worth International",
                    timezone = "America/Chicago",
                    iata = "DFW",
                    terminal = "A",
                    gate = "A2",
                    baggage = null,
                    delay = 2,
                    scheduled = "2019-12-12T08:32:00+00:00",
                    estimated = "2019-12-12T08:32:00+00:00",
                    actual = "2019-12-12T08:32:00+00:00",
                    estimatedRunway = null,
                    actualRunway = null
                ),
                airline = AirlineInfoEntity(
                    name = "American Airlines",
                    iata = "AA",
                    icao = null
                ),
                flight = FlightInfoEntity(
                    number = "1002",
                    iata = "AA1002",
                    icao = null,
                    codeshared = null
                ),
                aircraft = AircraftInfoEntity(
                    registration = "N160A2",
                    iata = "A321",
                    icao = "ABC",
                    icao24 = "ABC123"
                ),
                live = LiveInfoEntity(
                    updated = "2022-03-05T10:15:00",
                    latitude = 37.7749,
                    longitude = -122.4194,
                    altitude = 33000.0,
                    direction = 90.0,
                    speedHorizontal = 550.0,
                    speedVertical = 0.0,
                    isGround = false
                )
            ),

            FlightDataEntity(
                flightDate = "2019-12-13",
                flightStatus = "scheduled",
                price = 1584,
                currency = "USD",
                departure = DepartureEntity(
                    airport = "Los Angeles International",
                    timezone = "America/Los_Angeles",
                    iata = "LAX",
                    terminal = "2",
                    gate = "D1",
                    delay = 2,
                    scheduled = "2019-12-12T04:21:00+00:00",
                    estimated = null,
                    actual = null,
                    estimatedRunway = null,
                    actualRunway = null
                ),
                arrival = ArrivalEntity(
                    airport = "John F. Kennedy International",
                    timezone = "America/New_York",
                    iata = "JFK",
                    terminal = "B",
                    gate = "A1",
                    baggage = null,
                    delay = 1,
                    scheduled = "2019-12-12T08:31:00+00:00",
                    estimated = "2019-12-12T08:31:00+00:00",
                    actual = "2019-12-12T08:31:00+00:00",
                    estimatedRunway = null,
                    actualRunway = null
                ),
                airline = AirlineInfoEntity(
                    name = "Delta Air Lines",
                    iata = "DL",
                    icao = null
                ),
                flight = FlightInfoEntity(
                    number = "1001",
                    iata = "DL1001",
                    icao = null,
                    codeshared = null
                ),
                aircraft = AircraftInfoEntity(
                    registration = "N160A1",
                    iata = "B737",
                    icao = "XYZ",
                    icao24 = "XYZ123"
                ),
                live = LiveInfoEntity(
                    updated = "2022-03-05T10:00:00",
                    latitude = 34.0522,
                    longitude = -118.2437,
                    altitude = 35000.0,
                    direction = 120.0,
                    speedHorizontal = 500.0,
                    speedVertical = 0.0,
                    isGround = false
                )
            ),

            FlightDataEntity(
                flightDate = "2019-12-13",
                flightStatus = "scheduled",
                price = 9848,
                currency = "USD",
                departure = DepartureEntity(
                    airport = "Los Angeles International",
                    timezone = "America/Los_Angeles",
                    iata = "LAX",
                    terminal = "4",
                    gate = "D3",
                    delay = 6,
                    scheduled = "2019-12-12T04:23:00+00:00",
                    estimated = null,
                    actual = null,
                    estimatedRunway = null,
                    actualRunway = null
                ),
                arrival = ArrivalEntity(
                    airport = "John F. Kennedy International",
                    timezone = "America/New_York",
                    iata = "JFK",
                    terminal = "B",
                    gate = "A3",
                    baggage = null,
                    delay = 3,
                    scheduled = "2019-12-12T08:33:00+00:00",
                    estimated = "2019-12-12T08:33:00+00:00",
                    actual = "2019-12-12T08:33:00+00:00",
                    estimatedRunway = null,
                    actualRunway = null
                ),
                airline = AirlineInfoEntity(
                    name = "Delta Air Lines",
                    iata = "DL",
                    icao = null
                ),
                flight = FlightInfoEntity(
                    number = "1003",
                    iata = "DL1003",
                    icao = null,
                    codeshared = null
                ),
                aircraft = AircraftInfoEntity(
                    registration = "N160A3",
                    iata = "B737",
                    icao = "XYZ",
                    icao24 = "XYZ123"
                ),
                live = LiveInfoEntity(
                    updated = "2022-03-05T10:30:00",
                    latitude = 40.6413,
                    longitude = -73.7781,
                    altitude = 36000.0,
                    direction = 150.0,
                    speedHorizontal = 600.0,
                    speedVertical = 0.0,
                    isGround = false
                )
            ),
        )
        `when`(mockRemote.getFlights()).thenReturn(mockFlightsList)
        val result = repository.getFlights(FlightInputModel(sort = "price_low_high"))
        assertNotNull(result)
        assertTrue(result!!.map { it.price } == arrayListOf(1584,1776,9848))
    }

    @Test
    fun `getAirports with success`() = runBlocking {
        val mockFlightsList =  listOf(
            AirportsDataEntity(
                code = "JFK",
                lat = 40.6413,
                lon = -73.7781,
                name = "John F. Kennedy International Airport",
                city = "New York",
                state = "NY",
                country = "USA",
                woeId = "123456",
                tz = "America/New_York",
                phone = "+1 123-456-7890",
                type = "International",
                email = "info@jfkairport.com",
                url = "http://www.jfkairport.com",
                runwayLength = 12000,
                elev = 13,
                icao = "KJFK",
                directFlights = "200",
                carriers = "Delta, American, United"
            ),
            AirportsDataEntity(
                code = "LHR",
                lat = 51.4700,
                lon = -0.4543,
                name = "Heathrow Airport",
                city = "London",
                state = null,
                country = "United Kingdom",
                woeId = "654321",
                tz = "Europe/London",
                phone = "+44 20 1234 5678",
                type = "International",
                email = "info@heathrow.com",
                url = "http://www.heathrow.com",
                runwayLength = 11000,
                elev = 25,
                icao = "EGLL",
                directFlights = "150",
                carriers = "British Airways, Virgin Atlantic"
            ),
            AirportsDataEntity(
                code = "CDG",
                lat = 49.0097,
                lon = 2.5479,
                name = "Charles de Gaulle Airport",
                city = "Paris",
                state = null,
                country = "France",
                woeId = "789012",
                tz = "Europe/Paris",
                phone = "+33 1 2345 6789",
                type = "International",
                email = "info@cdgairport.fr",
                url = "http://www.charlesdegaulleairport.com",
                runwayLength = 13000,
                elev = 39,
                icao = "LFPG",
                directFlights = "180",
                carriers = "Air France, Lufthansa, Delta"
            )
        )
        `when`(mockRemote.getAirports()).thenReturn(mockFlightsList)
        val result = repository.getAirports()
        assertNotNull(result)
        assertEquals(3, result!!.size)
    }

}