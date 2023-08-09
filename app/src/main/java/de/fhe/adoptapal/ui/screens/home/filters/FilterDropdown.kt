package de.fhe.adoptapal.ui.screens.home.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color

/**
 * A Composable function that creates a filter dropdown menu.
 *
 * @param title The title text displayed on the dropdown button.
 * @param selectedValue The currently selected value to be displayed in the dropdown button.
 * @param options The list of available options for the dropdown.
 * @param onValueSelected The callback function to be invoked when an option is selected.
 */
@Composable
fun <T : Any> FilterDropdown(
    title: String,
    selectedValue: String,
    options: List<T>,
    onValueSelected: (String) -> Unit
) {
    // State variables to track dropdown menu status and selected option
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(selectedValue) }

    Button(
        onClick = { expanded = true },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        if (selectedOption.isNotBlank()) {
            // Display selected option and a clear button
            Text(
                text = "$title $selectedOption",
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear Selection",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .clickable {
                        selectedOption = ""
                        onValueSelected("")
                    }
                    .padding(end = 4.dp)
            )
        } else {
            // Display title only when no option is selected
            Text(
                text = title,
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground
            )
        }
    }

    // Dropdown menu content
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                // Update selected option and invoke the callback
                selectedOption =
                    if (option is AnimalCategory) option.name else (option as Color).name
                onValueSelected(if (option is AnimalCategory) option.name else (option as Color).name)
                expanded = false // Collapse the dropdown menu after selection
            }) {
                // Display option text in the menu
                Text(
                    text = if (option is AnimalCategory) option.name else (option as Color).name,
                    style = TextStyle(fontSize = 14.sp),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}
