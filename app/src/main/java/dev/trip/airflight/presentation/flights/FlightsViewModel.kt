package dev.trip.airflight.presentation.flights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.trip.airflight.common.utils.ResultResource
import dev.trip.airflight.domain.flights.model.AirportModel
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.model.FlightsModel
import dev.trip.airflight.domain.flights.usecases.GetAirportUseCases
import dev.trip.airflight.domain.flights.usecases.GetFlightUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(private val flightUseCase: GetFlightUseCases,private val getAirportUseCases: GetAirportUseCases) : ViewModel() {

    private val _flightStateFlow = MutableStateFlow(FlightState())
    val flightFlow: StateFlow<FlightState> = _flightStateFlow.asStateFlow()

    private val _airportsStateFlow = MutableStateFlow(AirportState())
    val airportsFlow: StateFlow<AirportState> = _airportsStateFlow.asStateFlow()

    private var jobAirport: Job? = null
    private var jobFlight: Job? = null

    init {
        getAirports()
    }


    fun getFlights(input: FlightInputModel) {
        jobFlight?.cancel()
        jobFlight = viewModelScope.launch(Dispatchers.IO) {
            _flightStateFlow.emit(_flightStateFlow.value.copy(loading = true, error = null))
            flightUseCase.invoke(input).apply {
                when (this) {
                    is ResultResource.Failure -> _flightStateFlow.emit(FlightState(loading = false, error = throwable))
                    is ResultResource.Success -> _flightStateFlow.tryEmit(FlightState(loading = false, flights = result, error = null))
                }
            }
        }
    }

      fun getAirports() {
        jobAirport?.cancel()
        jobAirport = viewModelScope.launch(Dispatchers.IO) {
            _airportsStateFlow.emit(_airportsStateFlow.value.copy(loading = true, error = null))
            getAirportUseCases.invoke().apply {
                when (this) {
                    is ResultResource.Failure -> _airportsStateFlow.emit(AirportState(loading = false, error = throwable))
                    is ResultResource.Success -> _airportsStateFlow.emit(AirportState(loading = false, airports = result, error = null))
                }
            }
        }
    }

    override fun onCleared() {
        jobAirport?.cancel()
        jobFlight?.cancel()
        super.onCleared()
    }
}

data class FlightState(val loading: Boolean = false, val flights: List<FlightsModel>? = null, val error: Throwable? = null)
data class AirportState(val loading: Boolean = false, val airports: List<AirportModel>? = null, val error: Throwable? = null)