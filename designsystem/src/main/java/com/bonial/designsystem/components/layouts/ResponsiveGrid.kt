package com.bonial.designsystem.components.layouts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.bonial.designsystem.tokens.Spacing

/**
 * Responsive grid layout that adjusts columns based on device orientation
 */
@Composable
fun <T> ResponsiveGrid(
    items: List<T>,
    itemContent: @Composable (T, Boolean) -> Unit,
    isItemFullWidth: (T) -> Boolean = { false },
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(Spacing.small)
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val columns = if (isLandscape) 3 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(
            count = items.size,
            key = { index -> items[index].hashCode() },
            span = { index ->
                val item = items[index]
                val isFullWidth = isItemFullWidth(item)
                val span = if (isFullWidth) columns else 1
                GridItemSpan(span)
            }
        ) { index ->
            val item = items[index]
            val isFullWidth = isItemFullWidth(item)
            itemContent(item, isFullWidth)
        }
    }
}