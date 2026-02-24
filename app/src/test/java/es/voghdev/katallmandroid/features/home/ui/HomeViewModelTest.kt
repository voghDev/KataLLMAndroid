package es.voghdev.katallmandroid.features.home.ui

import app.cash.turbine.test
import es.voghdev.katallmandroid.features.home.data.Llm
import es.voghdev.katallmandroid.features.home.data.LlmDataSource
import es.voghdev.katallmandroid.features.profile.data.ProfileDataSource
import es.voghdev.katallmandroid.features.profile.data.User
import es.voghdev.katallmandroid.features.subscription.data.SubscriptionDataSource
import es.voghdev.katallmandroid.features.subscription.data.UserSubscription
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
    private lateinit var llmDataSource: LlmDataSource
    private lateinit var profileDataSource: ProfileDataSource
    private lateinit var subscriptionDataSource: SubscriptionDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        llmDataSource = mockk()
        profileDataSource = mockk()
        subscriptionDataSource = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should start with loading state`() = runTest {
        every { llmDataSource.getLlms() } returns flow { }
        every { profileDataSource.getUser() } returns flow { }
        every { subscriptionDataSource.getUserSubscription() } returns flow { }

        val viewModel = HomeViewModel(llmDataSource, profileDataSource, subscriptionDataSource)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Loading, state)
        }
    }

    @Test
    fun `should fetch llms from data source on init`() = runTest {
        val expectedLlms = listOf(
            Llm(
                name = "Claude Sonnet 4",
                company = "Anthropic",
                releaseDate = "2025-05",
                description = "A balanced model",
            ),
        )
        val mockUser = User(name = "Jane Doe", address = "", phoneNumber = "", profilePictureUrl = "")
        every { llmDataSource.getLlms() } returns flow { emit(expectedLlms) }
        every { profileDataSource.getUser() } returns flow { emit(mockUser) }
        every { subscriptionDataSource.getUserSubscription() } returns flow { emit(UserSubscription.FREE) }

        val viewModel = HomeViewModel(llmDataSource, profileDataSource, subscriptionDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Success(expectedLlms), state)
        }

        verify { llmDataSource.getLlms() }
        verify { profileDataSource.getUser() }
        verify { subscriptionDataSource.getUserSubscription() }
    }

    @Test
    fun `should emit error state when data source fails`() = runTest {
        val mockUser = User(name = "Jane Doe", address = "", phoneNumber = "", profilePictureUrl = "")
        every { llmDataSource.getLlms() } returns flow { throw RuntimeException("Network error") }
        every { profileDataSource.getUser() } returns flow { emit(mockUser) }
        every { subscriptionDataSource.getUserSubscription() } returns flow { emit(UserSubscription.FREE) }

        val viewModel = HomeViewModel(llmDataSource, profileDataSource, subscriptionDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Error("Network error"), state)
        }

        verify { llmDataSource.getLlms() }
    }
}
