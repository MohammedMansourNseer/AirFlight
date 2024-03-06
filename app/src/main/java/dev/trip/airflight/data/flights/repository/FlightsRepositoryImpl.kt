package dev.trip.airflight.data.flights.repository

import dev.trip.airflight.data.flights.datasoruce.FlightRemoteDatasource
import dev.trip.airflight.data.flights.entity.mapperToAirportsModel
import dev.trip.airflight.data.flights.entity.mapperToFlightsModel
import dev.trip.airflight.domain.flights.model.AirportModel
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.model.FlightsModel
import dev.trip.airflight.domain.flights.model.SearchFlightInputModel
import dev.trip.airflight.domain.flights.repository.FlightsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FlightsRepositoryImpl @Inject constructor(
    private val flightRemote: FlightRemoteDatasource,
) : FlightsRepository {

    override suspend fun getFlights(input: FlightInputModel): List<FlightsModel>? =
        withContext(Dispatchers.IO) {
            val data = flightRemote.getFlights()?.map { it.mapperToFlightsModel() }
            data?.let { flights ->
                input.sort?.let {
                    when (it) {
                        "price_low_high" -> flights.sortedWith { a, b -> a.price!!.compareTo(b.price!!) }
                        "price_high_low" -> flights.sortedWith { a, b -> b.price!!.compareTo(a.price!!) }
                        "departure_time_ascending" -> flights.sortedWith { a, b -> a.flightDate.compareTo(b.flightDate) }
                        "departure_time_descending" -> flights.sortedWith { a, b -> a.flightDate.compareTo(b.flightDate) }
                        else -> flights
                    }
                } ?: kotlin.run { flights }
            }
        }

    override suspend fun getAirports(): List<AirportModel>? =
        withContext(Dispatchers.IO) {
            flightRemote.getAirports()?.map { it.mapperToAirportsModel() }
        }
}
