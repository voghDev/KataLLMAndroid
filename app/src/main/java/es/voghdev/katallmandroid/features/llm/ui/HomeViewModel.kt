package es.voghdev.katallmandroid.features.llm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.voghdev.katallmandroid.features.llm.data.LLM
import es.voghdev.katallmandroid.features.llm.data.LLMDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val llmDataSource: LLMDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadLLMs()
    }

    private fun loadLLMs() {
        viewModelScope.launch {
            llmDataSource.getLLMs()
                .catch { _uiState.value = HomeUiState.Error(it.message ?: "Unknown error") }
                .collect { llms -> _uiState.value = HomeUiState.Success(llms) }
        }
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val llms: List<LLM>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
