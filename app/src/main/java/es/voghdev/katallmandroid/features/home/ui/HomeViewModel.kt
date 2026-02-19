package es.voghdev.katallmandroid.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.voghdev.katallmandroid.features.home.data.Llm
import es.voghdev.katallmandroid.features.home.data.LlmDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val llmDataSource: LlmDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadLlms()
    }

    private fun loadLlms() {
        viewModelScope.launch {
            llmDataSource.getLlms()
                .catch { _uiState.value = HomeUiState.Error(it.message ?: "Unknown error") }
                .collect { llms -> _uiState.value = HomeUiState.Success(llms) }
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val llms: List<Llm>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
