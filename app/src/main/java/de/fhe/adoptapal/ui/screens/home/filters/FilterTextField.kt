package de.fhe.adoptapal.ui.screens.home.filters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A Composable function that displays a labeled outlined text field for filtering purposes.
 *
 * @param label The label displayed above the text field.
 * @param value The current value of the text field.
 * @param onValueChange The callback function to be invoked when the text field value changes.
 */
@Composable
fun FilterTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    // Display the label above the text field
    Text(text = label)

    // Create an outlined text field for input
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}
