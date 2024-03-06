package dev.trip.airflight.data.flights.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class AirportsResponseEntity(
    val data: List<AirportsDataEntity>?,
)

@Serializable()
data class AirportsDataEntity(
    val code: String,
    val lat: Double?,
    val lon: Double?,
    val name: String,
    val city: String,
    val state: String?,
    val country: String,
    @SerializedName("woeid")
    val woeId: String,
    val tz: String,
    val phone: String?,
    val type: String,
    val email: String?,
    val url: String?,
    @SerializedName("runway_length")
    val runwayLength: Int?,
    val elev: Int?,
    val icao: String,
    @SerializedName("direct_flights")
    val directFlights: String,
    val carriers: String,
)

