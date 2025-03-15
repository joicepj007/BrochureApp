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

    /*@Test
    fun `mapToDomainModel should correctly map response with array content`() {
        // Given
        // Mock JSON serialization/deserialization
        val contentWrapperList = listOf(
            ContentWrapper(
                content = ContentItem(
                    id = "1",
                    publisher = Publisher(id = "pub1", name = "Retailer 1"),
                    imageUrl = "https://example.com/image1.jpg",
                    distance = 3.5
                )
            )
        )

        `when`(gson.toJson(any())).thenReturn("[]")
        //`when`(gson.fromJson<List<ContentWrapper>>(anyString(), any())).thenReturn(contentWrapperList)

        val response = BrochureResponse(
            id = "response1",
            embedded = EmbeddedData(
                contents = listOf(
                    ContentPlacement(
                        contentType = "superBannerCarousel",
                        content = listOf<Any>() // Content type doesn't matter as we mock the gson behavior
                    )
                )
            )
        )

        // When
        val result = mapper.mapToDomainModel(response)

        // Then
        assertEquals(1, result.size)
        with(result[0]) {
            assertEquals("1", id)
            assertEquals("Retailer 1", retailer.name)
            assertEquals("superBannerCarousel", content.contentType)
            assertEquals("https://example.com/image1.jpg", content.brochureImage?.url)
            assertEquals(3.5, distance)
        }
    }

    @Test
    fun `mapToDomainModel should correctly map response with object content`() {
        // Given
        // Mock JSON serialization/deserialization
        val contentItem = ContentItem(
            id = "2",
            publisher = Publisher(id = "pub2", name = "Retailer 2"),
            brochureImage = "https://example.com/image2.jpg",
            distance = 4.2
        )

        `when`(gson.toJson(any())).thenReturn("{}")
        `when`(gson.fromJson(anyString(), any<Class<ContentItem>>())).thenReturn(contentItem)

        val response = BrochureResponse(
            id = "response2",
            embedded = EmbeddedData(
                contents = listOf(
                    ContentPlacement(
                        contentType = "brochure",
                        content = mapOf<String, Any>() // Content type doesn't matter as we mock the gson behavior
                    )
                )
            )
        )

        // When
        val result = mapper.mapToDomainModel(response)

        // Then
        assertEquals(1, result.size)
        with(result[0]) {
            assertEquals("2", id)
            assertEquals("Retailer 2", retailer.name)
            assertEquals("brochure", content.contentType)
            assertEquals("https://example.com/image2.jpg", content.brochureImage?.url)
            assertEquals(4.2, distance)
        }
    }*/

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