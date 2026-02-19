package es.voghdev.katallmandroid.features.llm.ui

import app.cash.turbine.test
import es.voghdev.katallmandroid.features.llm.data.LLM
import es.voghdev.katallmandroid.features.llm.data.LLMDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var llmDataSource: LLMDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        llmDataSource = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should fetch LLMs from data source on init`() = runTest {
        val expectedLLMs = listOf(
            LLM("GPT-4o", "OpenAI", "May 2024", "Multimodal flagship model."),
            LLM("Claude 3.5 Sonnet", "Anthropic", "June 2024", "High-performance model."),
        )

        every { llmDataSource.getLLMs() } returns flow { emit(expectedLLMs) }

        val viewModel = HomeViewModel(llmDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Success(expectedLLMs), state)
        }

        verify { llmDataSource.getLLMs() }
    }

    @Test
    fun `should emit error state when data source fails`() = runTest {
        every { llmDataSource.getLLMs() } returns flow { throw RuntimeException("Network error") }

        val viewModel = HomeViewModel(llmDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Error("Network error"), state)
        }

        verify { llmDataSource.getLLMs() }
    }

    @Test
    fun `should start with loading state`() = runTest {
        every { llmDataSource.getLLMs() } returns flow { /* never emits */ }

        val viewModel = HomeViewModel(llmDataSource)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Loading, state)
        }
    }
}
