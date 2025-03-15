package com.bonial.data.remote

import com.bonial.data.model.BrochureResponse

interface BrochureRemoteDataSource {
    suspend fun getBrochures(): Result<BrochureResponse>
}