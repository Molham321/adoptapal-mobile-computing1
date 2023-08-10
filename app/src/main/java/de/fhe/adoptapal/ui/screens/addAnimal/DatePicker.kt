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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import java.util.Calendar
import java.util.Date

@Composable
fun DatePicker(
    birthdateValue: String,
    onValueChange: (String) -> Unit
) {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()

    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            val correctMonth = month + 1
            val formattedDay = if (day < 10) "0$day" else "$day"
            val formattedMonth = if (correctMonth < 10) "0$correctMonth" else "$correctMonth"
            val formattedDate = "$formattedDay.$formattedMonth.$year"

            mDate.value = formattedDate
            onValueChange(formattedDate)
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
                Text(text = stringResource(R.string.birth_date), color = Color.White)
            } else {
                Text(text = "Geburtsdatum: $birthdateValue", color = Color.White)
            }
        }
    }
}
