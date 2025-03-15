package com.bonial.domain.usecase

import com.bonial.domain.model.BrochureItem
import com.bonial.domain.model.Content
import com.bonial.domain.model.Retailer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterBrochuresByDistanceUseCaseTest {

    private lateinit var filterBrochuresByDistanceUseCase: FilterBrochuresByDistanceUseCase

    @Before
    fun setup() {
        filterBrochuresByDistanceUseCase = FilterBrochuresByDistanceUseCase()
    }

    @Test
    fun `invoke should return only brochures within max distance`() {
        // Given
        val brochures = listOf(
            createBrochure("1", 2.5),
            createBrochure("2", 4.9),
            createBrochure("3", 5.0),
            createBrochure("4", 5.1),
            createBrochure("5", 10.0),
            createBrochure("6", null)
        )

        // When
        val filteredBrochures = filterBrochuresByDistanceUseCase(brochures, 5.0)

        // Then
        assertEquals(3, filteredBrochures.size)
        assertEquals("1", filteredBrochures[0].id)
        assertEquals("2", filteredBrochures[1].id)
        assertEquals("3", filteredBrochures[2].id)
    }

    @Test
    fun `invoke should return empty list for empty input`() {
        // Given
        val emptyList = emptyList<BrochureItem>()

        // When
        val result = filterBrochuresByDistanceUseCase(emptyList, 5.0)

        // Then
        assertEquals(0, result.size)
    }

    @Test
    fun `invoke should filter out items with null distance`() {
        // Given
        val brochures = listOf(
            createBrochure("1", null),
            createBrochure("2", null),
            createBrochure("3", 3.0)
        )

        // When
        val filteredBrochures = filterBrochuresByDistanceUseCase(brochures, 5.0)

        // Then
        assertEquals(1, filteredBrochures.size)
        assertEquals("3", filteredBrochures[0].id)
    }

    private fun createBrochure(id: String, distance: Double?): BrochureItem {
        return BrochureItem(
            id = id,
            retailer = Retailer(id = "r$id", name = "Retailer $id"),
            content = Content(contentType = "brochure", brochureImage = null),
            distance = distance
        )
    }
}