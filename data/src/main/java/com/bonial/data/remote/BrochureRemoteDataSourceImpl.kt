package com.bonial.data.remote

import android.util.Log
import com.bonial.data.model.BrochureResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BrochureRemoteDataSourceImpl @Inject constructor(
    private val apiService: BrochureApiService,
    private val gson: Gson
) : BrochureRemoteDataSource {

    override suspend fun getBrochures(): Result<BrochureResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getBrochures()
                Log.d(TAG, "API call successful")

                Result.success(response)
            } catch (e: Exception) {
                Log.e(TAG, "API call failed", e)
                Result.failure(e)
            }
        }
    }

    companion object {
        private const val TAG = "BrochureRemoteDataSource"
    }
}