package com.bonial.designsystem.theme

import androidx.compose.ui.graphics.Color

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

// Base colors
val Primary = Color(0xFF1976D2)
val PrimaryDark = Color(0xFF1565C0)
val PrimaryLight = Color(0xFF42A5F5)

val Secondary = Color(0xFFFF9800)
val SecondaryDark = Color(0xFFF57C00)
val SecondaryLight = Color(0xFFFFB74D)

val Background = Color(0xFFF5F5F5)
val Surface = Color(0xFFFFFFFF)
val ErrorColor = Color(0xFFB00020)

// Extended color palette for specific use cases
val BonialColors = object {
    val premiumBadgeBackground = Primary
    val premiumBadgeText = Color.White
    val placeholderBackground = Color(0xFFE0E0E0)
    val placeholderIcon = Color(0xFF9E9E9E)
}

/**
 * Extension property to access Bonial specific colors through MaterialTheme
 */
/*
val MaterialTheme.bonialColors: BonialColors
    @Composable
    @ReadOnlyComposable
    get() = BonialColors*/
