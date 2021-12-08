package com.example.watchlist2

import app.cash.turbine.test
import com.example.watchlist2.common.Resource
import com.example.watchlist2.domain.model.AnimeSearchResult
import com.example.watchlist2.domain.usecase.SearchAnimeUseCase
import com.example.watchlist2.presentation.search.AnimeSearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class AnimeSearchViewModelUnitTest {

    @MockK
    lateinit var searchAnimeUseCase: SearchAnimeUseCase

    @SpyK
    @InjectMockKs
    lateinit var animeSearchViewModel: AnimeSearchViewModel

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
    @Ignore("broken by `debounce`?")
    fun testViewModel_bad_1(): Unit = runBlocking {
        val loading = Resource.Loading<List<AnimeSearchResult>>()
        val success = Resource.Success<List<AnimeSearchResult>>(emptyList())

        every { searchAnimeUseCase("test") } returns flowOf(loading, success)

        animeSearchViewModel.uiState.test(timeout = Duration.seconds(5)) {
            awaitItem() // initial state value

            animeSearchViewModel.setCurrentQuery("test")

            assertEquals(loading, awaitItem())
            assertEquals(success, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

        animeSearchViewModel.setCurrentQuery("test")

        verify { searchAnimeUseCase("test") }
    }

    @Test
    @Ignore("broken by `debounce`?")
    fun testViewModel_bad_2(): Unit = runBlocking {
        val loading = Resource.Loading<List<AnimeSearchResult>>()
        val success = Resource.Success<List<AnimeSearchResult>>(emptyList())

        every { searchAnimeUseCase("test") } returns flowOf(loading, success)

        val job = launch(Dispatchers.Main) {
            animeSearchViewModel.uiState.collectIndexed { i, item ->
                if (i == 1) {
                    assertEquals(loading, item)
                }
                if (i == 2) {
                    assertEquals(success, item)
                    cancel()
                }
            }
        }

        animeSearchViewModel.setCurrentQuery("test")

        delay(10_000)
        job.cancelAndJoin()

        verify { searchAnimeUseCase("test") }
    }
}