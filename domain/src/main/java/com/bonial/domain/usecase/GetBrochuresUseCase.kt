package com.bonial.domain.usecase

import com.bonial.domain.model.BrochureItem
import com.bonial.domain.repository.BrochureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBrochuresUseCase @Inject constructor(
    private val repository: BrochureRepository
) {
    operator fun invoke(): Flow<Result<List<BrochureItem>>> {
        return repository.getBrochures()
    }
}