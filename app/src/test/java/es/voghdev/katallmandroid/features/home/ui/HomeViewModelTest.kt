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
    private lateinit var viewModel: HomeViewModel

    private val mockLlms = listOf(
        Llm(name = "Claude Sonnet 4", company = "Anthropic", releaseDate = "2025-05", description = "A balanced model"),
    )
    private val mockUser = User(name = "Jane Doe", address = "", phoneNumber = "", profilePictureUrl = "")

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

    private fun givenThereAreSomeLlms() = every { llmDataSource.getLlms() } returns flow { emit(mockLlms) }
    private fun givenThereAreNoLlms() = every { llmDataSource.getLlms() } returns flow { throw RuntimeException("Network error") }
    private fun givenTheUserProfileReturns(user: User) = every { profileDataSource.getUser() } returns flow { emit(user) }
    private fun givenTheUserHasSubscription(subscription: UserSubscription) = every { subscriptionDataSource.getUserSubscription() } returns flow { emit(subscription) }

    @Test
    fun `should start with loading state`() = runTest {
        givenThereAreSomeLlms()
        givenTheUserProfileReturns(mockUser)
        givenTheUserHasSubscription(UserSubscription.FREE)
        viewModel = HomeViewModel(llmDataSource, profileDataSource, subscriptionDataSource)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Loading, state)
        }
    }

    @Test
    fun `should fetch llms from data source on init`() = runTest {
        givenThereAreSomeLlms()
        givenTheUserProfileReturns(mockUser)
        givenTheUserHasSubscription(UserSubscription.FREE)
        viewModel = HomeViewModel(llmDataSource, profileDataSource, subscriptionDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Success(mockLlms), state)
        }

        verify { llmDataSource.getLlms() }
        verify { profileDataSource.getUser() }
        verify { subscriptionDataSource.getUserSubscription() }
    }

    @Test
    fun `should emit error state when data source fails`() = runTest {
        givenThereAreNoLlms()
        givenTheUserProfileReturns(mockUser)
        givenTheUserHasSubscription(UserSubscription.FREE)
        viewModel = HomeViewModel(llmDataSource, profileDataSource, subscriptionDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(HomeUiState.Error("Network error"), state)
        }

        verify { llmDataSource.getLlms() }
    }
}
