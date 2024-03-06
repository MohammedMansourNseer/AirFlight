package dev.trip.airflight.domain.flights.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FlightsModel(
    val flightDate: String,
    val flightStatus: String,
    val price: Int? = 0,
    val currency: String? = "USD",
    val departure: DepartureModel,
    val arrival: ArrivalModel,
    val airLine: AirlineModel,
    val flight: FlightModel,
) : Parcelable

@Parcelize
data class DepartureModel(
    val airport: String,
    val timeZone: String,
    val terminal: String,
    val gate: String,
    val scheduledDate: String?,
    val estimatedDate: String?,
    val delay: Int,
    val actualDate: String?,
) : Parcelable

@Parcelize
data class ArrivalModel(
    val airport: String,
    val timeZone: String,
    val terminal: String,
    val gate: String,
    val baggage: String?,
    val scheduledDate: String?,
    val estimatedDate: String?,
    val delay: Int,
    val actualDate: String?,
) : Parcelable

@Parcelize
data class AirlineModel(val name: String, val iata: String, val icao: String?) : Parcelable

@Parcelize
data class FlightModel(val number: String, val iata: String, val icao: String?) : Parcelable

data class FlightInputModel(val search: SearchFlightInputModel? = null, val sort: String? = null)

data class SearchFlightInputModel(val departure: String = "", val arrival: String = "", val departureDate: String = "", val arrivalDate: String = "")

