package com.bonial.designsystem.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bonial.designsystem.components.loading.LoadingIndicator
import com.bonial.designsystem.components.error.ErrorState
import com.bonial.designsystem.components.state.EmptyState

/**
 * Standard list screen template with common states (loading, error, empty, content)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> StandardListScreen(
    title: String,
    items: List<T>,
    isLoading: Boolean,
    error: String? = null,
    emptyMessage: String,
    onRetry: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    content: @Composable (List<T>) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                actions = { actions() }
            )
        },
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    LoadingIndicator()
                }
                error != null -> {
                    ErrorState(
                        message = error,
                        onRetry = onRetry,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                items.isEmpty() -> {
                    EmptyState(
                        message = emptyMessage,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    content(items)
                }
            }
        }
    }
}