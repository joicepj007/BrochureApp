package com.bonial.data.remote

import com.bonial.data.model.BrochureResponse
import retrofit2.http.GET

interface BrochureApiService {
    @GET("shelf.json")
    suspend fun getBrochures(): BrochureResponse
}