package dev.trip.airflight.presentation.flights

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dev.trip.airflight.R
import dev.trip.airflight.common.based.delegate.viewBinding
import dev.trip.airflight.common.extensions.hiddenKeyboard
import dev.trip.airflight.common.extensions.launchFragment
import dev.trip.airflight.common.extensions.snackBar
import dev.trip.airflight.common.extensions.visibleOrGone
import dev.trip.airflight.databinding.FragmentFlightBinding
import dev.trip.airflight.domain.flights.model.AirportModel
import dev.trip.airflight.domain.flights.model.FlightInputModel
import dev.trip.airflight.domain.flights.model.SearchFlightInputModel
import dev.trip.airflight.presentation.flights.adpater.FlightAdapter
import dev.trip.airflight.presentation.flights.sheets.flightSortBottomSheet
import com.google.android.material.radiobutton.MaterialRadioButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import showDatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class FlightFragment : Fragment(R.layout.fragment_flight) {

    private val viewModel: FlightsViewModel by viewModels()

    private val binding: FragmentFlightBinding by viewBinding(FragmentFlightBinding::bind)

    private var searchInput: SearchFlightInputModel = SearchFlightInputModel()
    private var flightInput = FlightInputModel(search = searchInput)
    private val adapter: FlightAdapter by lazy {
        FlightAdapter {
            launchFragment(FlightFragmentDirections.actionFlightFragmentToFlightDetailsFragment(it))
        }
    }

    private var startDateMillis: Long? = null
    private var sortValue = "recommended"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindFlightList()
        binding.bindClicks()
        binding.collectFlightsList()
        binding.collectAirportList()
    }

    private fun FragmentFlightBinding.bindFlightList() {
        rvFlights.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.getFlights(flightInput.copy(search = searchInput, sort = sortValue))
            }
        }
    }

    private fun validateFields(): Boolean {
        if (searchInput.departure.isEmpty()) {
            snackBar(getString(R.string.please_fill_departure_field))
            return false
        }
        if (searchInput.arrival.isEmpty()) {
            snackBar(getString(R.string.please_fill_arrival_field))
            return false
        }
        if (searchInput.departureDate.isEmpty()) {
            snackBar(getString(R.string.please_select_from_date))
            return false
        }

        if (searchInput.arrivalDate.isEmpty()) {
            snackBar(getString(R.string.please_select_to_date))
            return false
        }
        return true
    }

    private fun FragmentFlightBinding.bindClicks() {
        searchBtn.setOnClickListener {
            requireContext().hiddenKeyboard(it)
            if (validateFields())
                viewModel.getFlights(flightInput.copy(search = searchInput, sort = sortValue))
        }

        dateFromBtn.setOnClickListener {
            requireContext().hiddenKeyboard(it)
            showDatePickerDialog(requireContext(), System.currentTimeMillis()) { year, month, dayOfMonth ->
                val calendar = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                startDateMillis = calendar.timeInMillis // Store start date
                searchInput = searchInput.copy(departureDate = "$year-$month-$dayOfMonth")
                val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                dateFromBtn.text = dateFormat.format(calendar.time)
            }
        }

        dateToBtn.setOnClickListener {
            requireContext().hiddenKeyboard(it)
            startDateMillis?.let { minDate ->
                // Use the selected start date as min date for the end date picker
                showDatePickerDialog(requireContext(), minDate) { year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    searchInput = searchInput.copy(arrivalDate = "$year-$month-$dayOfMonth")
                    val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
                    dateToBtn.text = dateFormat.format(calendar.time)
                }
            } ?: run {
                // If startDateMillis is null, show some error or fallback to the current time
                snackBar(getString(R.string.select_start_date_first))

            }
        }

        sortBtn.setOnClickListener {
            flightSortBottomSheet { sheet, sortBinding ->
                sheet.show()
                with(sortBinding) {
                    (sortGroup.findViewWithTag(sortValue) as MaterialRadioButton).apply {
                        this.isChecked = true
                    }

                    sortGroup.setOnCheckedChangeListener { group, checkedId ->
                        val button = group.findViewById<MaterialRadioButton>(checkedId)
                        sortValue = button.tag as String
                        viewModel.getFlights(flightInput.copy(search = searchInput, sort = sortValue))
                        sheet.dismiss()
                    }
                }
            }
        }

        tryAgainBtn.setOnClickListener {
            if (viewModel.airportsFlow.value.airports == null) {
                viewModel.getAirports()
                return@setOnClickListener
            }

            if (viewModel.flightFlow.value.flights == null && validateFields()) {
                viewModel.getFlights(flightInput)
            }
        }
    }

    private fun FragmentFlightBinding.collectFlightsList() {
        lifecycleScope.launch {
            viewModel.flightFlow.collectLatest {
                with(it) {
                    swipeRefreshLayout.isRefreshing = loading && (flights?.isNotEmpty() == true)
                    errorText.visibleOrGone(false)
                    circleLoading.visibleOrGone(loading && flights == null)
                    tryAgainBtn.visibleOrGone(false)

                    error?.apply {
                        if (adapter.itemCount > 0) {
                            errorText.visibleOrGone(false)
                            snackBar(localizedMessage ?: getString(R.string.unknown_exception_please_try_again))
                        } else {
                            errorText.visibleOrGone(true)
                            errorText.text = localizedMessage ?: getString(R.string.unknown_exception_please_try_again)
                        }
                    }

                    flights?.apply {
                        if (isNotEmpty()) {
                            sortBtn.visibleOrGone(true)
                            errorText.visibleOrGone(false)
                            rvFlights.visibleOrGone(true)
                            adapter.setData(flights)
                        }
                    }

                }
            }


        }
    }

    private fun FragmentFlightBinding.bindAutoCompletes(airportList: List<AirportModel>) {
        with(airportList) {
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                map { airport -> "${airport.name}\n${airport.city} - ${airport.country}" })
                .apply {
                    atDeparture.setOnItemClickListener { _, _, position, _ ->
                        searchInput = searchInput.copy(departure = get(position).name)
                    }
                    atArrival.setOnItemClickListener { _, _, position, _ ->
                        searchInput = searchInput.copy(arrival = get(position).name)
                    }
                    atDeparture.setAdapter(this)
                    atArrival.setAdapter(this)
                }
        }
    }

    private fun FragmentFlightBinding.collectAirportList() {
        lifecycleScope.launch {
            viewModel.airportsFlow.collectLatest {
                with(it) {
                    errorText.visibleOrGone(false)
                    circleLoading.visibleOrGone(loading && airports == null)
                    tryAgainBtn.visibleOrGone(false)

                    error?.apply {
                        if (adapter.itemCount > 0) {
                            errorText.visibleOrGone(false)
                            snackBar(localizedMessage ?: getString(R.string.unknown_exception_please_try_again))
                        } else {
                            tryAgainBtn.visibleOrGone(true)
                            errorText.visibleOrGone(true)
                            errorText.text = localizedMessage ?: getString(R.string.unknown_exception_please_try_again)
                        }
                    }

                    airports?.apply {
                        if (isNotEmpty()) {
                            header.visibleOrGone(true)
                            errorText.visibleOrGone(false)
                            bindAutoCompletes(this)
                        } else {
                            errorText.visibleOrGone(true)
                            errorText.text = getString(R.string.not_found_airport_error_msg)

                        }
                    }

                }
            }
        }

    }
}
