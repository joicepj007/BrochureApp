package com.bonial.data.repository

import android.util.Log
import com.bonial.data.mapper.BrochureMapper
import com.bonial.data.remote.BrochureRemoteDataSource
import com.bonial.domain.model.BrochureItem
import com.bonial.domain.repository.BrochureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class BrochureRepositoryImpl @Inject constructor(
    private val remoteDataSource: BrochureRemoteDataSource,
    private val mapper: BrochureMapper
) : BrochureRepository {

    override fun getBrochures(): Flow<Result<List<BrochureItem>>> = flow {
        try {
            val result = remoteDataSource.getBrochures()

            if (result.isSuccess) {
                // Get the response
                val response = result.getOrThrow()
                Log.d(TAG, "Successfully received API response")

                // Map to domain model
                val brochures = mapper.mapToDomainModel(response)
                Log.d(TAG, "Mapped ${brochures.size} brochures from API response")

                // Filter brochure types
                val filteredBrochures = brochures.filter {
                    mapper.isBrochureContentType(it.content.contentType)
                }
                Log.d(TAG, "Filtered to ${filteredBrochures.size} brochures with valid content types")

                emit(Result.success(filteredBrochures))
            } else {
                val exception = result.exceptionOrNull() ?: Exception("Unknown error")
                Log.e(TAG, "Error fetching brochures", exception)
                emit(Result.failure(exception))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getBrochures", e)
            emit(Result.failure(e))
        }
    }

    companion object {
        private const val TAG = "BrochureRepository"
    }
}