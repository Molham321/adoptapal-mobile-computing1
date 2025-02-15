package de.fhe.adoptapal.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColorPalette = lightColors(
    primary = MainBlue,
    primaryVariant = LightModeSecondary,
    secondary = LightModeSecondary,
    background = BackgroundWhite,
)
@Composable
fun AndroidAdoptapalTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}