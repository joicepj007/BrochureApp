package com.bonial.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    primaryContainer = PrimaryLight,
    secondary = Secondary,
    secondaryContainer = SecondaryLight,
    background = Background,
    surface = Surface,
    error = ErrorColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    primaryContainer = Primary,
    secondary = SecondaryLight,
    secondaryContainer = Secondary,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = ErrorColor
)

/**
 * Bonial theme implementation for consistent styling
 */
@Composable
fun BonialAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Not using Material You dynamic colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BonialTypography,
        shapes = BonialShapes,
        content = content
    )
}