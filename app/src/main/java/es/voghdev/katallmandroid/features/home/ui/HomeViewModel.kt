package es.voghdev.katallmandroid.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.voghdev.katallmandroid.features.home.data.Llm
import es.voghdev.katallmandroid.features.home.data.LlmDataSource
import es.voghdev.katallmandroid.features.profile.data.ProfileDataSource
import es.voghdev.katallmandroid.features.subscription.data.SubscriptionDataSource
import es.voghdev.katallmandroid.core.catchAndHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val llmDataSource: LlmDataSource,
    private val profileDataSource: ProfileDataSource,
    private val subscriptionDataSource: SubscriptionDataSource,
    private val onError: @JvmSuppressWildcards (Throwable) -> Unit,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                llmDataSource.getLlms(),
                profileDataSource.getUser(),
                subscriptionDataSource.getUserSubscription(),
            ) { llms, _, _ -> llms }
                .catchAndHandle(onError) { e ->
                    _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
                }
                .collect { llms -> _uiState.value = HomeUiState.Success(llms) }
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val llms: List<Llm>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
