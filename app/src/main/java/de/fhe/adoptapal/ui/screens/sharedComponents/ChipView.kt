package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A Composable function that displays a chip view with gender information.
 *
 * @param gender Whether the gender is male (true) or female (false).
 * @param colorResource The color resource to be used for the chip's background and text color.
 */
@Composable
fun ChipView(gender: Boolean, colorResource: Color) {
    // Determine the gender value based on the boolean gender parameter
    val genderValue: String = if (gender) {
        "Männlich"
    } else {
        "Weiblich"
    }
    // Display the chip view with a rounded corner shape, background color, and gender text
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource.copy(.08f))
    ) {

        Text(
            text = genderValue, modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
            style = MaterialTheme.typography.caption,
            color = colorResource
        )
    }
}