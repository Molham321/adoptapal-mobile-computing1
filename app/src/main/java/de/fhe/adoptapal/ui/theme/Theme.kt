package de.fhe.adoptapal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

// TODO: create theming here

private val DarkColorPalette = darkColors(
    primary = MainBlue,
    primaryVariant = DarkModeSecondary,
    secondary = DarkModeSecondary,
    background = DarkModeBackground
)

private val LightColorPalette = lightColors(
    primary = MainBlue,
    primaryVariant = LightModeSecondary,
    secondary = LightModeSecondary,
    background = BackgroundWhite,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AndroidAdoptapalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}