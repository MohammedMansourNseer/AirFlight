package dev.trip.airflight.domain.flights.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AirportModel(
    val code: String,
    val lat: Double?,
    val lon: Double?,
    val name: String,
    val city: String,
    val state: String?,
    val country: String,
    val woeId: String,
    val tz: String,
    val phone: String?,
    val type: String,
    val email: String?,
    val url: String?,
    val runwayLength: Int?,
    val elev: Int?,
    val icao: String,
    val directFlights: String,
    val carriers: String
) : Parcelable

