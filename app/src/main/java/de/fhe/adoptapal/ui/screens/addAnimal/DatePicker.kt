package de.fhe.adoptapal.ui.screens.addAnimal

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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

// Creating a composable function to
// create two Images and a spacer between them
// Calling this function as content
// in the above function
@Composable
fun DatePicker(
    birthdateValue: String,
    editing: Boolean = false,
    onValueChange: (String) -> Unit
) {
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
            // mDate.value = "$mDayOfMonth.${mMonth+1}.$mYear"
            val correctMonth = mMonth + 1
            if (correctMonth < 10) {
                if (mDay < 10) {
                    mDate.value = "0$mDay.0$correctMonth.$mYear"
                } else {
                    // mDate.value = "$mYear-0$correctMonth-$mDay"
                    mDate.value = "$mDay.0$correctMonth.$mYear"
                }
            } else {
                if (mDay < 10) {
                    mDate.value = "0$mDay.$correctMonth.$mYear"
                } else {
                    // mDate.value = "$mYear-0$correctMonth-$mDay"
                    mDate.value = "$mDay.$correctMonth.$mYear"
                }
            }
            // mDate.value = "$mYear-${mMonth+1}-$mDay"
            onValueChange(mDate.value)
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Creating a button that on
        // click displays/shows the DatePickerDialog
        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray),
            onClick = {
                mDatePickerDialog.show()
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            if (birthdateValue == "") {
                Text(text = "Geburtsdatum", color = Color.Gray)
            } else {
                Text(text = "Geburtsdatum: " + birthdateValue, color = Color.Gray)
            }
        }

        // Adding a space of 100dp height
//        Spacer(modifier = Modifier.size(100.dp))
//
//        // Displaying the mDate value in the Text
//        Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
    }
}