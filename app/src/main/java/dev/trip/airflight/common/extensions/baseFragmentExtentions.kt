package dev.trip.airflight.common.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import android.app.DatePickerDialog
import android.content.Context
import java.util.*


fun Fragment.launchFragment(navDirections: NavDirections) {
    val navDestination = NavHostFragment.findNavController(this).currentDestination
    if (navDestination?.getAction(navDirections.actionId) != null && navDestination.id != navDirections.actionId)
        NavHostFragment.findNavController(this).navigate(navDirections)
}

fun Fragment.snackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}


