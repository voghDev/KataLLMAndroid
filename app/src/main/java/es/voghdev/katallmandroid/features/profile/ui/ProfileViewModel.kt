package es.voghdev.katallmandroid.features.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.voghdev.katallmandroid.features.profile.data.ProfileDataSource
import es.voghdev.katallmandroid.features.profile.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDataSource: ProfileDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileDataSource.getUser()
                .catch { _uiState.value = ProfileUiState.Error(it.message ?: "Unknown error") }
                .collect { user -> _uiState.value = ProfileUiState.Success(user) }
        }
    }
}

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val user: User) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}
