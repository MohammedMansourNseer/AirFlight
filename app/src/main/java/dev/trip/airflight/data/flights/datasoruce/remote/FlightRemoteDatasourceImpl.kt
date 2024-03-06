package dev.trip.airflight.data.flights.datasoruce.remote

import dev.trip.airflight.data.flights.datasoruce.FlightRemoteDatasource
import dev.trip.airflight.data.flights.datasoruce.remote.network.ApiService
import dev.trip.airflight.data.flights.entity.AirportsDataEntity
import dev.trip.airflight.data.flights.entity.AirportsResponseEntity
import dev.trip.airflight.data.flights.entity.FlightDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import javax.inject.Inject


class FlightRemoteDatasourceImpl @Inject constructor(private val apiService: ApiService) : FlightRemoteDatasource {

    override suspend fun getFlights(): List<FlightDataEntity>? = apiService.getFlights().let {
        if (it.isSuccessful) {
            return it.body()?.data
        } else {
            throw throwException(it.errorBody())
        }
    }

    override suspend fun getAirports(): List<AirportsDataEntity>? = apiService.getAirports().let {
        if (it.isSuccessful) {
            return it.body()?.data
        } else {
            throw throwException(it.errorBody())
        }
    }

    private fun throwException(response: ResponseBody?): Exception {
        val gson = Gson().fromJson<HashMap<String, Any>>(response?.string(), object : TypeToken<HashMap<String, Any>>() {}.type)
        return Exception(gson["message"].toString())
    }
}

