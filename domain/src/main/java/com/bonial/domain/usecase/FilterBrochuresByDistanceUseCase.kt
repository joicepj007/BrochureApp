package com.bonial.domain.usecase

import com.bonial.domain.model.BrochureItem
import javax.inject.Inject

class FilterBrochuresByDistanceUseCase @Inject constructor() {
    operator fun invoke(brochures: List<BrochureItem>, maxDistance: Double): List<BrochureItem> {
        return brochures.filter { it.distance != null && it.distance <= maxDistance }
    }
}