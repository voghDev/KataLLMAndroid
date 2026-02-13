package es.voghdev.katallmandroid.features.profile.ui

import app.cash.turbine.test
import es.voghdev.katallmandroid.features.profile.data.ProfileDataSource
import es.voghdev.katallmandroid.features.profile.data.User
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
class ProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var profileDataSource: ProfileDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        profileDataSource = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should fetch user from data source on init`() = runTest {
        val expectedUser = User(
            name = "Jane Doe",
            address = "742 Evergreen Terrace, Springfield",
            phoneNumber = "+1 (555) 123-4567",
            profilePictureUrl = "",
        )

        every { profileDataSource.getUser() } returns flow { emit(expectedUser) }

        val viewModel = ProfileViewModel(profileDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(ProfileUiState.Success(expectedUser), state)
        }

        verify { profileDataSource.getUser() }
    }

    @Test
    fun `should emit error state when data source fails`() = runTest {
        every { profileDataSource.getUser() } returns flow { throw RuntimeException("Network error") }

        val viewModel = ProfileViewModel(profileDataSource)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(ProfileUiState.Error("Network error"), state)
        }

        verify { profileDataSource.getUser() }
    }

    @Test
    fun `should start with loading state`() = runTest {
        every { profileDataSource.getUser() } returns flow { /* never emits */ }

        val viewModel = ProfileViewModel(profileDataSource)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(ProfileUiState.Loading, state)
        }
    }
}
