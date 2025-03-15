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
            Log.d(TAG, "Fetching brochures from API")
            val result = remoteDataSource.getBrochures()

            if (result.isSuccess) {
                // Get the response
                val response = result.getOrThrow()
                Log.d(TAG, "Successfully received API response")

                // Detailed debugging about the response structure
                response.embedded?.let { embedded ->
                    Log.d(TAG, "Found ${embedded.contents.size} content placements")

                    embedded.contents.forEachIndexed { index, placement ->
                        Log.d(TAG, "Content placement $index: type=${placement.contentType}, placement=${placement.placement}")

                        // Check content structure
                        val content = placement.content
                        when (content) {
                            is List<*> -> Log.d(TAG, "Content is a list with ${content.size} items")
                            is Map<*, *> -> Log.d(TAG, "Content is a direct object with ${content.size} keys")
                            else -> Log.d(TAG, "Content is of unknown type: ${content?.javaClass?.simpleName}")
                        }
                    }
                } ?: Log.d(TAG, "Embedded data is null")

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