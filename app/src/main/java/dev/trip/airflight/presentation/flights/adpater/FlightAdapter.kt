package dev.trip.airflight.presentation.flights.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.trip.airflight.common.based.adapter.BaseRecyclerAdapter
import dev.trip.airflight.databinding.FlightAdapterItemBinding
import dev.trip.airflight.domain.flights.model.FlightsModel
import formatTo2DecimalPlaces

class FlightAdapter(private val onClicked: (FlightsModel) -> Unit) : BaseRecyclerAdapter<FlightAdapterItemBinding, FlightsModel>() {

    override fun bindObject(obj: FlightsModel, v: FlightAdapterItemBinding, holder: RecyclerView.ViewHolder) {
        with(v) {
            // departure trip info
            tvDepartureAirPort.text = obj.departure.airport
            tvDepartureDate.text = obj.departure.gate

            // arrival trip info
            tvArrivalAirPort.text = obj.arrival.airport
            tvArrivalDate.text = obj.arrival.gate

            // other info
            tvAirLine.text = obj.airLine.name
            tvFlightNumber.text = obj.flight.number

            // price info
            tvPrice.text = obj.price?.toDouble()?.formatTo2DecimalPlaces()
            tvPriceCurrency.text = obj.currency

            root.setOnClickListener {
                onClicked(obj)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FlightAdapterItemBinding, FlightsModel> =
        BaseViewHolder(FlightAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

}
