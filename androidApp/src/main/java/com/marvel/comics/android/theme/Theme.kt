package com.marvel.comics.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkPrimaryColor,
    primaryVariant = darkPrimaryVariantColor,
    secondary = darkSecondaryColor
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = primaryVariantColor,
    secondary = secondaryColor
)

@Composable
fun MarvelComicsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(content = content, colors = colors, typography = MarvelTypography)
}
