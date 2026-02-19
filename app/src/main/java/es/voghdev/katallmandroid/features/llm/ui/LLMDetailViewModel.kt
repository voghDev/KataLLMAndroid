package es.voghdev.katallmandroid.features.llm.ui

import androidx.lifecycle.SavedStateHandle
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
class LLMDetailViewModel @Inject constructor(
    private val llmDataSource: LLMDataSource,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LLMDetailUiState>(LLMDetailUiState.Loading)
    val uiState: StateFlow<LLMDetailUiState> = _uiState

    init {
        val index = savedStateHandle.get<Int>("index") ?: 0
        loadLLMDetail(index)
    }

    private fun loadLLMDetail(index: Int) {
        viewModelScope.launch {
            llmDataSource.getLLMByIndex(index)
                .catch { _uiState.value = LLMDetailUiState.Error(it.message ?: "Unknown error") }
                .collect { llm -> _uiState.value = LLMDetailUiState.Success(llm) }
        }
    }
}

sealed interface LLMDetailUiState {
    data object Loading : LLMDetailUiState
    data class Success(val llm: LLM) : LLMDetailUiState
    data class Error(val message: String) : LLMDetailUiState
}
