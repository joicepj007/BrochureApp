package com.bonial.domain.repository

import com.bonial.domain.model.BrochureItem
import kotlinx.coroutines.flow.Flow

interface BrochureRepository {
    fun getBrochures(): Flow<Result<List<BrochureItem>>>
}
