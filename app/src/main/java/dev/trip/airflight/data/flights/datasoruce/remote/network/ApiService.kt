package dev.trip.airflight.data.flights.datasoruce.remote.network

import dev.trip.airflight.data.flights.entity.AirportsResponseEntity
import dev.trip.airflight.data.flights.entity.FlightResponseEntity
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/allFlightsTripe")
    suspend fun getFlights(): Response<FlightResponseEntity>

    @GET("api/allAirports")
    suspend fun getAirports(): Response<AirportsResponseEntity>

}