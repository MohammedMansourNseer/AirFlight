package dev.trip.airflight.domain.flights.repository

import dev.trip.airflight.domain.flights.model.AirportModel
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.model.FlightsModel
import dev.trip.airflight.domain.flights.model.SearchFlightInputModel

interface FlightsRepository {
    suspend fun getFlights(input: FlightInputModel): List<FlightsModel>?
    suspend fun getAirports(): List<AirportModel>?

}
