package com.bonial.domain.usecase

import com.bonial.domain.model.BrochureImage
import com.bonial.domain.model.BrochureItem
import com.bonial.domain.model.Content
import com.bonial.domain.model.Retailer
import com.bonial.domain.repository.BrochureRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetBrochuresUseCaseTest {

    @Mock
    private lateinit var brochureRepository: BrochureRepository

    private lateinit var getBrochuresUseCase: GetBrochuresUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getBrochuresUseCase = GetBrochuresUseCase(brochureRepository)
    }

    @Test
    fun `invoke should return brochures from repository`() = runBlocking {
        // Given
        val expectedBrochures = listOf(
            createBrochure("1", "Retailer A", "brochure", 2.5),
            createBrochure("2", "Retailer B", "brochurePremium", 3.7)
        )
        `when`(brochureRepository.getBrochures()).thenReturn(flowOf(Result.success(expectedBrochures)))

        // When
        val result = getBrochuresUseCase().first()

        // Then
        assert(result.isSuccess)
        assertEquals(expectedBrochures, result.getOrNull())
    }

    @Test
    fun `invoke should propagate error from repository`() = runBlocking {
        // Given
        val expectedError = Exception("Network error")
        `when`(brochureRepository.getBrochures()).thenReturn(flowOf(Result.failure(expectedError)))

        // When
        val result = getBrochuresUseCase().first()

        // Then
        assert(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }

    private fun createBrochure(id: String, retailerName: String, contentType: String, distance: Double?): BrochureItem {
        return BrochureItem(
            id = id,
            retailer = Retailer(id = "r$id", name = retailerName),
            content = Content(contentType = contentType, brochureImage = BrochureImage("https://example.com/image$id.jpg")),
            distance = distance
        )
    }
}