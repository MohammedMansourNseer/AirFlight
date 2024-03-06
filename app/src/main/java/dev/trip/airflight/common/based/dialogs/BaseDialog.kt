import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.Fragment
import java.util.Calendar

fun Number.formatTo2DecimalPlaces(): String = String.format("%.2f", this.toDouble())

fun Fragment.showDatePickerDialog(requireContext: Context, minDate: Long, onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(requireContext, { _, selectedYear, selectedMonth, selectedDay ->
        onDateSelected(selectedYear, selectedMonth, selectedDay)
    }, year, month, dayOfMonth)

    datePickerDialog.datePicker.minDate = minDate
    datePickerDialog.show()
}
