package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import  de.fhe.adoptapal.R

@Composable
fun GenderTag(isMale: Boolean) {
    val color = if (isMale === true) R.color.blue else R.color.red
    ChipView(gender = isMale, colorResource = colorResource(id = color))
}
