package com.bonial.data.mapper

import com.bonial.data.model.BrochureResponse
import com.bonial.data.model.ContentItem
import com.bonial.data.model.ContentPlacement
import com.bonial.data.model.ContentWrapper
import com.bonial.data.model.EmbeddedData
import com.bonial.data.model.Publisher
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.any

class BrochureMapperTest {

    @Mock
    private lateinit var gson: Gson

    private lateinit var mapper: BrochureMapper

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mapper = BrochureMapper()

        // Inject the mocked Gson using reflection for testing
        val gsonField = BrochureMapper::class.java.getDeclaredField("gson")
        gsonField.isAccessible = true
        gsonField.set(mapper, gson)
    }

    @Test
    fun `isBrochureContentType should return true for valid content types`() {
        // Then
        assertTrue(mapper.isBrochureContentType("brochure"))
        assertTrue(mapper.isBrochureContentType("brochurePremium"))
        assertTrue(mapper.isBrochureContentType("superBannerCarousel"))
    }

    @Test
    fun `isBrochureContentType should return false for invalid content types`() {
        // Then
        assertFalse(mapper.isBrochureContentType("otherType"))
        assertFalse(mapper.isBrochureContentType(null))
    }

    private fun assertTrue(condition: Boolean) {
        org.junit.Assert.assertTrue(condition)
    }

    private fun assertFalse(condition: Boolean) {
        org.junit.Assert.assertFalse(condition)
    }
}