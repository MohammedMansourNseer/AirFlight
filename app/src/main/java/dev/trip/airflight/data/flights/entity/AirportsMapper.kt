package dev.trip.airflight.data.flights.entity

import dev.trip.airflight.domain.flights.model.AirportModel

fun AirportsDataEntity.mapperToAirportsModel() = AirportModel(
    code = code,
    lat = lat,
    lon = lon,
    name = name,
    city = city,
    state = state,
    country = country,
    woeId = woeId,
    tz = tz,
    phone = phone,
    type = type,
    email = email,
    url = url,
    runwayLength = runwayLength,
    elev = elev,
    icao = icao,
    directFlights = directFlights,
    carriers = carriers
)
