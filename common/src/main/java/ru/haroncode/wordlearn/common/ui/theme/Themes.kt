package ru.haroncode.wordlearn.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Yellow400 = Color(0xFFF6E547)
private val Yellow600 = Color(0xFFF5CF1B)
private val Yellow700 = Color(0xFFF3B711)
private val Yellow800 = Color(0xFFF29F05)

private val Blue200 = Color(0xFF9DA3FA)
private val Blue400 = Color(0xFF4860F7)
private val Blue500 = Color(0xFF0540F2)
private val Blue800 = Color(0xFF001CCF)

private val Red300 = Color(0xFFEA6D7E)
private val Red800 = Color(0xFFD00036)

private val BcColor = Color(0xFFF9F4F0)

private val AntiqueBrassColor = Color(0xFFffe8d6)
private val LeatherColor = Color(0xFFccb6a5)


private val RewiseDarkPalette = darkColors(
    primary = Blue200,
    primaryVariant = Blue400,
    onPrimary = Color.Black,
    secondary = Yellow400,
    onSecondary = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    error = Red300,
    onError = Color.Black
)

private val RewiseLightPalette = lightColors(
    primary = Color(0xFFffe8d6),
    primaryVariant = Color(0xFFccb6a5),
    surface = Color(0xFFDDBEA9),
    onPrimary = Color.Black,
    secondary = Color(0xFFcb997e),
    secondaryVariant = Color(0xFF996b52),
    onSecondary = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black,
    error = Red800,
    onError = Color.White
)

@Composable
fun RewiseTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit
) {
    val themeColors = colors ?: if (isDarkTheme) RewiseDarkPalette else RewiseLightPalette

    MaterialTheme(
        colors = themeColors,
        content = content,
        typography = RewiseTypography,
        shapes = RewiseShapes
    )
}
