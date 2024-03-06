package dev.trip.airflight.data.flights.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class FlightResponseEntity(
    val data: List<FlightDataEntity>?,
)

@Serializable()
data class FlightDataEntity(
    @SerializedName("flight_date")
    val flightDate: String,
    @SerializedName("flight_status")
    val flightStatus: String,
    val price: Int,
    val currency: String,
    val departure: DepartureEntity,
    val arrival: ArrivalEntity,
    val airline: AirlineInfoEntity,
    val flight: FlightInfoEntity,
    val aircraft: AircraftInfoEntity,
    val live: LiveInfoEntity,
)

@Serializable()
data class DepartureEntity(
    val airport: String,
    val timezone: String,
    val iata: String,
    val terminal: String,
    val gate: String,
    val delay: Int,
    val scheduled: String?,
    val estimated: String?,
    val actual: String?,
    @SerializedName("estimated_runway")
    val estimatedRunway: String?,
    @SerializedName("actual_runway")
    val actualRunway: String?,
)

@Serializable()
data class ArrivalEntity(
    val airport: String,
    val timezone: String,
    val iata: String,
    val terminal: String,
    val gate: String,
    val baggage: String?,
    val delay: Int,
    val scheduled: String,
    val estimated: String,
    val actual: String,
    @SerializedName("estimated_runway")
    val estimatedRunway: String?,
    @SerializedName("actual_runway")
    val actualRunway: String?,
)

@Serializable()
data class AirlineInfoEntity(
    val name: String,
    val iata: String,
    val icao: String?,
)

@Serializable()

data class FlightInfoEntity(
    val number: String,
    val iata: String,
    val icao: String?,
    val codeshared: String?,
)

@Serializable()
data class AircraftInfoEntity(
    val registration: String,
    val iata: String,
    val icao: String,
    val icao24: String,
)

@Serializable()
data class LiveInfoEntity(
    val updated: String,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val direction: Double,
    val speedHorizontal: Double,
    val speedVertical: Double,
    val isGround: Boolean,
)