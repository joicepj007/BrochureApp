package com.bonial.designsystem.components.filters

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.bonial.designsystem.tokens.Spacing

/**
 * Filter toggle switch with label
 */
@Composable
fun FilterSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(end = Spacing.medium)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            modifier = Modifier.padding(end = Spacing.small)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}