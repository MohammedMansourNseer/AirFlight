package dev.trip.airflight.domain.flights.usecases

import dev.trip.airflight.common.utils.ResultResource
import dev.trip.airflight.domain.flights.model.AirportModel
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.model.FlightsModel
import dev.trip.airflight.domain.flights.repository.FlightsRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GetFlightUseCases @Inject constructor(private val repository: FlightsRepository) {
    suspend fun invoke(input: FlightInputModel): ResultResource<List<FlightsModel>?> = kotlin.runCatching {
        ResultResource.Success(repository.getFlights(input))
    }.getOrElse {
        if (it is UnknownHostException || it is ConnectException) {
            return ResultResource.Failure(Exception("Make sure you are connected to the Internet"))
        }
        if (it is SocketTimeoutException) {
            return ResultResource.Failure(Exception("Website down please try again"))
        }
        return ResultResource.Failure(it)
    }

}

class GetAirportUseCases @Inject constructor(private val repository: FlightsRepository) {
    suspend fun invoke(): ResultResource<List<AirportModel>?> = kotlin.runCatching {
        ResultResource.Success(repository.getAirports())
    }.getOrElse {
        if (it is UnknownHostException || it is ConnectException) {
            return ResultResource.Failure(Exception("Make sure you are connected to the Internet"))
        }
        if (it is SocketTimeoutException) {
            return ResultResource.Failure(Exception("Website down please try again"))
        }
        return ResultResource.Failure(it)
    }

}




