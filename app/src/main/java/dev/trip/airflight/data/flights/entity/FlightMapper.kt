package dev.trip.airflight.data.flights.entity

import dev.trip.airflight.domain.flights.model.AirlineModel
import dev.trip.airflight.domain.flights.model.ArrivalModel
import dev.trip.airflight.domain.flights.model.DepartureModel
import dev.trip.airflight.domain.flights.model.FlightModel
import dev.trip.airflight.domain.flights.model.FlightsModel

fun FlightDataEntity.mapperToFlightsModel() = FlightsModel(
    flightDate = flightDate,
    flightStatus = flightStatus,
    price = price,
    currency = currency,
    departure = departure.mapperToDepartureModel(),
    arrival = arrival.mapperToArrivalModel(),
    flight = flight.mapperToFlightModel(),
    airLine = airline.mapperToAirlineModel()
)

fun AirlineInfoEntity.mapperToAirlineModel() = AirlineModel(
    name, iata, icao
)

fun FlightInfoEntity.mapperToFlightModel() = FlightModel(
    number, iata, icao
)

fun DepartureEntity.mapperToDepartureModel() = DepartureModel(
    airport = airport, timeZone = timezone, terminal = terminal, gate = gate,
    scheduledDate = scheduled, estimatedDate = estimated, delay = delay, actualDate = actual
)

fun ArrivalEntity.mapperToArrivalModel() = ArrivalModel(
    airport = airport, timeZone = timezone, terminal = terminal, gate = gate, baggage = baggage,
    scheduledDate = scheduled, estimatedDate = estimated, delay = delay, actualDate = actual
)