package com.bonial.presentation.navigation.brochurelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bonial.presentation.R
import com.bonial.presentation.navigation.components.BrochureItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrochureListScreen(
    viewModel: BrochureListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFilterActive by viewModel.isFilterActive.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.brochures)) },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.filter),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Switch(
                            checked = isFilterActive,
                            onCheckedChange = { viewModel.toggleDistanceFilter(it) }
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_message)+"${uiState.error}",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadBrochures() }) {
                            Text(stringResource(id = R.string.retry))
                        }
                    }
                }
                uiState.brochures.isEmpty() -> {
                    Text(
                        text = stringResource(id = R.string.no_brochures),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    // Use a different number of columns based on orientation
                    val columns = if (isLandscape) 3 else 2

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(
                            items = uiState.brochures,
                            key = { it.id },
                            // Correctly use span parameter here
                            span = { brochure ->
                                val isPremium = brochure.content.contentType == "brochurePremium"
                                val span = if (isPremium && !isLandscape) columns else 1
                                GridItemSpan(span)
                            }
                        ) { brochure ->
                            val isPremium = brochure.content.contentType == "brochurePremium"

                            BrochureItemCard(
                                brochure = brochure,
                                isPremium = isPremium
                            )
                        }
                    }
                }
            }
        }
    }
}