package dev.trip.airflight.presentation.flights.sheets

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import dev.trip.airflight.R
import dev.trip.airflight.databinding.SortFlightBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


@SuppressLint("InflateParams")
fun Fragment.flightSortBottomSheet(bindInfo: (BottomSheetDialog, bottomBinding: SortFlightBottomSheetBinding) -> Unit) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.ThemeOverlay_App_BottomSheetDialogWhite)
    val view = LayoutInflater.from(requireContext()).inflate(R.layout.sort_flight_bottom_sheet, null)
    val binding: SortFlightBottomSheetBinding = SortFlightBottomSheetBinding.bind(view)
    bottomSheetDialog.behavior.isHideable = true
    bottomSheetDialog.behavior.isDraggable = true
    bottomSheetDialog.setContentView(binding.root)
    bindInfo(bottomSheetDialog, binding)
}