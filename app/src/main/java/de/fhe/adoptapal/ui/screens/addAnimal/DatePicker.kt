package de.fhe.adoptapal.ui.screens.addAnimal

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

@Composable
fun DatePicker(
    birthdateValue: String,
    editing: Boolean = false,
    onValueChange: (String) -> Unit
) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
            val correctMonth = mMonth + 1
            if (correctMonth < 10) {
                if (mDay < 10) {
                    mDate.value = "0$mDay.0$correctMonth.$mYear"
                } else {
                    mDate.value = "$mDay.0$correctMonth.$mYear"
                }
            } else {
                if (mDay < 10) {
                    mDate.value = "0$mDay.$correctMonth.$mYear"
                } else {
                    mDate.value = "$mDay.$correctMonth.$mYear"
                }
            }
            onValueChange(mDate.value)
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                mDatePickerDialog.show()
            }
        ) {
            if (birthdateValue == "") {
                Text(text = "* Geburtsdatum", color = Color.White)
            } else {
                Text(text = "Geburtsdatum: " + birthdateValue, color = Color.White)
            }
        }
    }
}