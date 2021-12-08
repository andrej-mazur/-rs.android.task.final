package com.example.watchlist2

import app.cash.turbine.test
import com.example.watchlist2.common.Resource
import com.example.watchlist2.domain.model.AnimeDetails
import com.example.watchlist2.domain.usecase.GetAnimeDetailsUseCase
import com.example.watchlist2.presentation.details.AnimeDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class AnimeDetailsViewModelUnitTest {

    @MockK
    lateinit var getAnimeDetailsUseCase: GetAnimeDetailsUseCase

    @SpyK
    @InjectMockKs
    lateinit var animeDetailsViewModel: AnimeDetailsViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUpDispatcher() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDownDispatcher() = Dispatchers.resetMain()

    @Before
    fun setUpMockK() = MockKAnnotations.init(this)

    @After
    fun tearDownMockK() = unmockkAll()

    @Test
    fun testViewModel(): Unit = runBlocking {
        val animeDetails = AnimeDetails(id = 0, title = "test")

        val loading = Resource.Loading<AnimeDetails>()
        val success = Resource.Success(animeDetails)

        every { getAnimeDetailsUseCase(0) } returns flowOf(loading, success)

        animeDetailsViewModel.uiState.test {
            awaitItem() // initial state value

            animeDetailsViewModel.setCurrentId(0)

            assertEquals(loading, awaitItem())
            assertEquals(success, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

        verify { getAnimeDetailsUseCase(0) }
    }
}