package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import  de.fhe.adoptapal.R

/**
 * A Composable function that displays a gender tag chip, indicating whether the gender is male or not.
 *
 * @param isMale Whether the gender is male (true) or not (false).
 */
@Composable
fun GenderTag(isMale: Boolean) {
    val color = if (isMale) R.color.blue else R.color.red
    ChipView(gender = isMale, colorResource = colorResource(id = color))
}
