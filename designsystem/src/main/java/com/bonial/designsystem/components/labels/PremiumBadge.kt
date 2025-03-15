package com.bonial.designsystem.components.labels

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bonial.designsystem.R
import com.bonial.designsystem.tokens.Spacing

/**
 * Premium badge component used to highlight premium content
 */
@Composable
fun PremiumBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = stringResource(id = R.string.premium),
            modifier = Modifier.padding(horizontal = Spacing.xsmall, vertical = Spacing.xxsmall),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}