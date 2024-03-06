package dev.trip.airflight.presentation.flightDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.trip.airflight.R
import dev.trip.airflight.common.based.delegate.viewBinding
import dev.trip.airflight.databinding.FragmentFlightDetailsBinding
import dev.trip.airflight.domain.flights.model.FlightsModel
import dagger.hilt.android.AndroidEntryPoint
import formatTo2DecimalPlaces

@AndroidEntryPoint
class FlightDetailsFragment : Fragment(R.layout.fragment_flight_details) {

    private val binding: FragmentFlightDetailsBinding by viewBinding(FragmentFlightDetailsBinding::bind)

    private lateinit var flight: FlightsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FlightDetailsFragmentArgs.fromBundle(requireArguments()).apply {
            this@FlightDetailsFragment.flight = flight
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindContent()
    }

    private fun FragmentFlightDetailsBinding.bindContent() {
        tvDepartureAirPort.text = flight.departure.airport
        tvDepartureDate.text    = flight.departure.gate

        tvArrivalAirPort.text = flight.arrival.airport
        tvArrivalDate.text    = flight.arrival.gate

        tvAirLine.text      = flight.airLine.name
        tvFlightNumber.text = flight.flight.number

        tvPrice.text         =  flight.price?.toDouble()?.formatTo2DecimalPlaces()
        tvPriceCurrency.text = flight.currency
    }

}