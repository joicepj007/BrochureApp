package com.bonial.presentation.brochurelist

import app.cash.turbine.test
import com.bonial.domain.model.BrochureImage
import com.bonial.domain.model.BrochureItem
import com.bonial.domain.model.Content
import com.bonial.domain.model.Retailer
import com.bonial.domain.usecase.FilterBrochuresByDistanceUseCase
import com.bonial.domain.usecase.GetBrochuresUseCase
import com.bonial.presentation.navigation.brochurelist.BrochureListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyList
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class BrochureListViewModelTest {

    @Mock
    private lateinit var getBrochuresUseCase: GetBrochuresUseCase

    @Mock
    private lateinit var filterBrochuresByDistanceUseCase: FilterBrochuresByDistanceUseCase

    private lateinit var viewModel: BrochureListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load brochures`() = runTest {
        // Given
        val brochures = listOf(
            createBrochure("1", "brochure"),
            createBrochure("2", "brochurePremium")
        )
        `when`(getBrochuresUseCase()).thenReturn(flowOf(Result.success(brochures)))

        // When
        viewModel = BrochureListViewModel(getBrochuresUseCase, filterBrochuresByDistanceUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(brochures, state.brochures)
            assertFalse(state.isLoading)
            assertNull(state.error)
        }
    }

    @Test
    fun `init should handle error when loading brochures`() = runTest {
        // Given
        val errorMessage = "Network error"
        `when`(getBrochuresUseCase()).thenReturn(flowOf(Result.failure(Exception(errorMessage))))

        // When
        viewModel = BrochureListViewModel(getBrochuresUseCase, filterBrochuresByDistanceUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.brochures.isEmpty())
            assertFalse(state.isLoading)
            assertEquals(errorMessage, state.error)
        }
    }

    @Test
    fun `toggleDistanceFilter with true should filter brochures`() = runTest {
        // Given
        val allBrochures = listOf(
            createBrochure("1", "brochure", 3.0),
            createBrochure("2", "brochure", 6.0)
        )
        val filteredBrochures = listOf(
            createBrochure("1", "brochure", 3.0)
        )

        `when`(getBrochuresUseCase()).thenReturn(flowOf(Result.success(allBrochures)))
        `when`(filterBrochuresByDistanceUseCase.invoke(anyList(), anyDouble())).thenReturn(filteredBrochures)

        viewModel = BrochureListViewModel(getBrochuresUseCase, filterBrochuresByDistanceUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleDistanceFilter(true)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(filteredBrochures, state.brochures)
        }

        viewModel.isFilterActive.test {
            val isActive = awaitItem()
            assertTrue(isActive)
        }

        verify(filterBrochuresByDistanceUseCase).invoke(allBrochures, 5.0)
    }

    @Test
    fun `toggleDistanceFilter with false should restore all brochures`() = runTest {
        // Given
        val allBrochures = listOf(
            createBrochure("1", "brochure", 3.0),
            createBrochure("2", "brochure", 6.0)
        )
        val filteredBrochures = listOf(
            createBrochure("1", "brochure", 3.0)
        )

        `when`(getBrochuresUseCase()).thenReturn(flowOf(Result.success(allBrochures)))
        `when`(filterBrochuresByDistanceUseCase.invoke(anyList(), anyDouble())).thenReturn(filteredBrochures)

        viewModel = BrochureListViewModel(getBrochuresUseCase, filterBrochuresByDistanceUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Apply filter first
        viewModel.toggleDistanceFilter(true)
        testDispatcher.scheduler.advanceUntilIdle()

        // When - Remove filter
        viewModel.toggleDistanceFilter(false)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(allBrochures, state.brochures)
        }

        viewModel.isFilterActive.test {
            val isActive = awaitItem()
            assertFalse(isActive)
        }
    }

    @Test
    fun `applyDistanceFilter should do nothing when brochure list is empty`() = runTest {
        // Given
        `when`(getBrochuresUseCase()).thenReturn(flowOf(Result.success(emptyList())))

        viewModel = BrochureListViewModel(getBrochuresUseCase, filterBrochuresByDistanceUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.applyDistanceFilter(5.0)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - FilterUseCase should not be called
        verify(filterBrochuresByDistanceUseCase, org.mockito.Mockito.never()).invoke(anyList(), anyDouble())
    }

    @Test
    fun `loadBrochures should update state when loading`() = runTest {
        // Given
        val brochures = listOf(createBrochure("1", "brochure"))
        `when`(getBrochuresUseCase()).thenReturn(flowOf(Result.success(brochures)))

        // Create ViewModel (it will auto-load)
        viewModel = BrochureListViewModel(getBrochuresUseCase, filterBrochuresByDistanceUseCase)

        // Wait for initial loading to complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Start testing from a known state
        viewModel.uiState.test {
            // Skip the already emitted state from initialization
            val currentState = awaitItem()
            assertFalse(currentState.isLoading)

            // When - reload
            viewModel.loadBrochures()

            // Then - loading state
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            // Advance time to complete loading
            testDispatcher.scheduler.advanceUntilIdle()

            // Then - loaded state
            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertEquals(brochures, loadedState.brochures)

            // Make sure we consume all events
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createBrochure(id: String, contentType: String, distance: Double? = null): BrochureItem {
        return BrochureItem(
            id = id,
            retailer = Retailer(id = "r$id", name = "Retailer $id"),
            content = Content(
                contentType = contentType,
                brochureImage = BrochureImage(url = "https://example.com/image$id.jpg")
            ),
            distance = distance
        )
    }
}