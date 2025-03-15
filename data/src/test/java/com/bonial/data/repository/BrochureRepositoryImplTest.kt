package com.bonial.data.repository

import app.cash.turbine.test
import com.bonial.data.mapper.BrochureMapper
import com.bonial.data.model.BrochureResponse
import com.bonial.data.model.ContentPlacement
import com.bonial.data.model.EmbeddedData
import com.bonial.data.remote.BrochureRemoteDataSource
import com.bonial.domain.model.BrochureImage
import com.bonial.domain.model.BrochureItem
import com.bonial.domain.model.Content
import com.bonial.domain.model.Retailer
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class BrochureRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: BrochureRemoteDataSource

    @Mock
    private lateinit var mapper: BrochureMapper

    private lateinit var repository: BrochureRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Mock Android Log class
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { android.util.Log.e(any(), any()) } returns 0
        every { android.util.Log.e(any(), any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
        repository = BrochureRepositoryImpl(remoteDataSource, mapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getBrochures should emit success with mapped items when remote source succeeds`() = runTest {
        // Given
        val mockResponse = createMockResponse()
        val mockDomainItems = listOf(
            createMockDomainItem("1", "brochure"),
            createMockDomainItem("2", "brochurePremium")
        )

        `when`(remoteDataSource.getBrochures()).thenReturn(Result.success(mockResponse))
        `when`(mapper.mapToDomainModel(mockResponse)).thenReturn(mockDomainItems)
        `when`(mapper.isBrochureContentType("brochure")).thenReturn(true)
        `when`(mapper.isBrochureContentType("brochurePremium")).thenReturn(true)

        // When & Then
        repository.getBrochures().test {
            // Advance the test dispatcher to execute coroutines
            testDispatcher.scheduler.advanceUntilIdle()

            val result = awaitItem()
            assert(result.isSuccess)
            assertEquals(mockDomainItems, result.getOrNull())
            awaitComplete()
        }
    }

    // Remaining test helper methods...


    // Remaining test helper methods...

    @Test
    fun `getBrochures should filter items based on content type`() = runTest {
        // Given
        val mockResponse = createMockResponse()
        val allDomainItems = listOf(
            createMockDomainItem("1", "brochure"),
            createMockDomainItem("2", "brochurePremium"),
            createMockDomainItem("3", "other")
        )

        val expectedFilteredItems = listOf(
            createMockDomainItem("1", "brochure"),
            createMockDomainItem("2", "brochurePremium")
        )

        `when`(remoteDataSource.getBrochures()).thenReturn(Result.success(mockResponse))
        `when`(mapper.mapToDomainModel(mockResponse)).thenReturn(allDomainItems)
        `when`(mapper.isBrochureContentType("brochure")).thenReturn(true)
        `when`(mapper.isBrochureContentType("brochurePremium")).thenReturn(true)
        `when`(mapper.isBrochureContentType("other")).thenReturn(false)

        // When & Then
        repository.getBrochures().test {
            val result = awaitItem()
            assert(result.isSuccess)
            assertEquals(expectedFilteredItems, result.getOrNull())
            awaitComplete()
        }
    }

    @Test
    fun `getBrochures should emit failure when remote source fails`() = runTest {
        // Given
        val expectedError = Exception("Network error")
        `when`(remoteDataSource.getBrochures()).thenReturn(Result.failure(expectedError))

        // When & Then
        repository.getBrochures().test {
            val result = awaitItem()
            assert(result.isFailure)
            assertEquals(expectedError, result.exceptionOrNull())
            awaitComplete()
        }
    }

    private fun createMockResponse(): BrochureResponse {
        return BrochureResponse(
            id = "response-id",
            embedded = EmbeddedData(
                contents = listOf(
                    ContentPlacement(
                        contentType = "brochure",
                        content = Any() // Content doesn't matter as we're mocking the mapper
                    )
                )
            )
        )
    }

    private fun createMockDomainItem(id: String, contentType: String): BrochureItem {
        return BrochureItem(
            id = id,
            retailer = Retailer(id = "r$id", name = "Retailer $id"),
            content = Content(contentType = contentType, brochureImage = BrochureImage("https://example.com/image$id.jpg")),
            distance = 3.5
        )
    }
}