package com.bonial.presentation.navigation.brochurelist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonial.designsystem.components.cards.BrochureCard
import com.bonial.designsystem.components.filters.FilterSwitch
import com.bonial.designsystem.components.layouts.ResponsiveGrid
import com.bonial.designsystem.components.screens.StandardListScreen
import com.bonial.presentation.R

// Example of using ResponsiveGrid with StandardListScreen
@Composable
fun BrochureListScreen(
    viewModel: BrochureListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFilterActive by viewModel.isFilterActive.collectAsStateWithLifecycle()

    StandardListScreen(
        title = stringResource(id = R.string.brochures),
        items = uiState.brochures,
        isLoading = uiState.isLoading,
        error = uiState.error,
        emptyMessage = stringResource(id = R.string.no_brochures),
        onRetry = { viewModel.loadBrochures() },
        actions = {
            FilterSwitch(
                label = stringResource(id = R.string.filter),
                checked = isFilterActive,
                onCheckedChange = { viewModel.toggleDistanceFilter(it) }
            )
        },
        content = { brochures ->
            ResponsiveGrid(
                items = brochures,
                isItemFullWidth = { brochure ->
                    brochure.content.contentType == "brochurePremium"
                },
                itemContent = { brochure, isFullWidth ->
                    val isPremium = brochure.content.contentType == "brochurePremium"
                    BrochureCard(
                        retailerName = brochure.retailer.name,
                        imageUrl = brochure.content.brochureImage?.url,
                        isPremium = isPremium,
                        distance = brochure.distance
                    )
                }
            )
        }
    )
}