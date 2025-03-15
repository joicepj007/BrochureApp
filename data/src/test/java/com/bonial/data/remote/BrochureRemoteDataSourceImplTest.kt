package com.bonial.data.remote

import com.bonial.data.model.BrochureResponse
import com.bonial.data.model.EmbeddedData
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class BrochureRemoteDataSourceImplTest {

    @Mock
    private lateinit var apiService: BrochureApiService

    @Mock
    private lateinit var gson: Gson

    private lateinit var remoteDataSource: BrochureRemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = BrochureRemoteDataSourceImpl(apiService, gson)
    }

    @Test
    fun `getBrochures should return success result when API call succeeds`() = runTest {
        // Given
        val mockResponse = BrochureResponse(
            id = "test-response",
            embedded = EmbeddedData(emptyList())
        )
        `when`(apiService.getBrochures()).thenReturn(mockResponse)

        // When
        val result = remoteDataSource.getBrochures()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(mockResponse, result.getOrNull())
    }

    @Test
    fun `getBrochures should return failure result when API call fails`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        `when`(apiService.getBrochures()).thenThrow(exception)

        // When
        val result = remoteDataSource.getBrochures()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}