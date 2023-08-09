package de.fhe.adoptapal.ui.screens.home.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A Composable function that displays a gender filter with selectable options.
 *
 * @param selectedGender The currently selected gender.
 * @param genderOptions The list of available gender options.
 * @param onGenderSelected The callback function to be invoked when a gender is selected.
 */
@Composable
fun GenderFilter(
    selectedGender: String,
    genderOptions: List<String>,
    onGenderSelected: (String) -> Unit
) {
    // Display the label for the gender filter
    Text(text = "Geschlecht:")

    // Create a row layout to display gender options
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        genderOptions.forEach { gender ->
            // Create a clickable box for each gender option
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onGenderSelected(gender)
                    }
                    .background(
                        color = getGenderBackgroundColor(gender, selectedGender),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // Display the gender option text inside the box
                Text(
                    text = gender,
                    color = if (gender == selectedGender) Color.White else Color.Black,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

/**
 * A helper function to determine the background color of the gender filter options.
 */
@Composable
private fun getGenderBackgroundColor(gender: String, selectedGender: String): Color {
    return when (gender) {
        "Männlich" -> if (selectedGender == gender) Color.Blue else Color.Transparent
        "Weiblich" -> if (selectedGender == gender) Color.Red else Color.Transparent
        "Alle" -> if (selectedGender == gender) Color.Gray else Color.Transparent
        else -> Color.Transparent
    }
}
