package dev.trip.airflight.common.extensions

import android.view.View

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
