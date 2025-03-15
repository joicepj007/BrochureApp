package com.bonial.designsystem.components.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Standardized loading indicator
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    fullScreen: Boolean = true
) {
    Box(
        modifier = if (fullScreen) modifier.fillMaxSize() else modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}