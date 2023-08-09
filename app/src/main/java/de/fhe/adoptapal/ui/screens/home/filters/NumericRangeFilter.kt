package de.fhe.adoptapal.ui.screens.home.filters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

/**
 * A Composable function that displays a numeric range filter with input fields.
 *
 * @param title The title text displayed above the range input fields.
 * @param rangeFrom The starting value of the numeric range.
 * @param rangeTo The ending value of the numeric range.
 * @param onRangeFromChange The callback function to be invoked when the starting value changes.
 * @param onRangeToChange The callback function to be invoked when the ending value changes.
 */
@Composable
fun NumericRangeFilter(
    title: String,
    rangeFrom: Int,
    rangeTo: Int,
    onRangeFromChange: (Int) -> Unit,
    onRangeToChange: (Int) -> Unit
) {
    // Display the title above the range input fields
    Text(text = title)

    // Create a row layout to display the numeric range input fields
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Input field for the starting value of the range
        OutlinedTextField(
            value = rangeFrom.toString(),
            onValueChange = { onRangeFromChange(it.toIntOrNull() ?: 0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )

        // Display "bis" text between the input fields
        Text(text = "bis")

        // Input field for the ending value of the range
        OutlinedTextField(
            value = rangeTo.toString(),
            onValueChange = { onRangeToChange(it.toIntOrNull() ?: 0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}
