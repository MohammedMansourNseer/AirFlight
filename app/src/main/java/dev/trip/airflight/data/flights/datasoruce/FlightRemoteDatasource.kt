package dev.trip.airflight.data.flights.datasoruce

import dev.trip.airflight.data.flights.entity.AirportsDataEntity
import dev.trip.airflight.data.flights.entity.FlightDataEntity

interface FlightRemoteDatasource {

    suspend fun getFlights(): List<FlightDataEntity>?
    suspend fun getAirports(): List<AirportsDataEntity>?

}
