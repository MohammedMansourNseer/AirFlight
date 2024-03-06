package dev.trip.airflight.common.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Context.hiddenKeyboard(v: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(v.windowToken, 0)
}

fun Context.showKeyboard(v: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.showKeyboard(editTexT: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    editTexT.requestFocus()
    imm.showSoftInput(editTexT, InputMethodManager.SHOW_IMPLICIT)

    editTexT.setSelection(editTexT.text.length)
}