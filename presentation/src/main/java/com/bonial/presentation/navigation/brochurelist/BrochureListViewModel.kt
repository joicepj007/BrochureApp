package com.bonial.presentation.navigation.brochurelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonial.domain.model.BrochureItem
import com.bonial.domain.usecase.FilterBrochuresByDistanceUseCase
import com.bonial.domain.usecase.GetBrochuresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrochureListViewModel @Inject constructor(
    private val getBrochuresUseCase: GetBrochuresUseCase,
    private val filterBrochuresByDistanceUseCase: FilterBrochuresByDistanceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BrochureListUiState())
    val uiState: StateFlow<BrochureListUiState> = _uiState.asStateFlow()

    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()

    private var allBrochures: List<BrochureItem> = emptyList()

    init {
        loadBrochures()
    }

    fun loadBrochures() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getBrochuresUseCase().collect { result ->
                result.fold(
                    onSuccess = { brochures ->
                        allBrochures = brochures
                        _uiState.update {
                            it.copy(
                                brochures = brochures,
                                isLoading = false
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "Unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun toggleDistanceFilter(isActive: Boolean) {
        viewModelScope.launch {
            _isFilterActive.value = isActive

            // Force UI refresh by briefly showing loading state
            _uiState.update { it.copy(isLoading = true) }
            delay(10) // Very brief delay

            if (isActive) {
                val filteredBrochures = filterBrochuresByDistanceUseCase(allBrochures, 5.0)
                _uiState.update { it.copy(brochures = filteredBrochures, isLoading = false) }
            } else {
                _uiState.update { it.copy(brochures = allBrochures.toList(), isLoading = false) }
            }
        }
    }

    fun applyDistanceFilter(maxDistance: Double = 5.0) {
        if (allBrochures.isEmpty()) return

        val filteredBrochures = filterBrochuresByDistanceUseCase(allBrochures, maxDistance)
        _uiState.update { it.copy(brochures = filteredBrochures) }
    }
}

data class BrochureListUiState(
    val brochures: List<BrochureItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)