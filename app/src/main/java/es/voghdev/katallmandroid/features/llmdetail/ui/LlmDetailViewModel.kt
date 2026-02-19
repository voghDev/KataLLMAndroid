package es.voghdev.katallmandroid.features.llmdetail.ui

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
class LlmDetailViewModel @Inject constructor(
    private val llmDataSource: LlmDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LlmDetailUiState>(LlmDetailUiState.Loading)
    val uiState: StateFlow<LlmDetailUiState> = _uiState

    fun loadLlm(index: Int) {
        viewModelScope.launch {
            llmDataSource.getLlms()
                .catch { _uiState.value = LlmDetailUiState.Error(it.message ?: "Unknown error") }
                .collect { llms ->
                    val llm = llms.getOrNull(index)
                    _uiState.value = if (llm != null) {
                        LlmDetailUiState.Success(llm)
                    } else {
                        LlmDetailUiState.Error("LLM not found")
                    }
                }
        }
    }
}

sealed interface LlmDetailUiState {
    data object Loading : LlmDetailUiState
    data class Success(val llm: Llm) : LlmDetailUiState
    data class Error(val message: String) : LlmDetailUiState
}
