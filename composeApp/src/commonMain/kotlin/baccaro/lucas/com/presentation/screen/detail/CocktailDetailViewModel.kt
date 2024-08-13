package baccaro.lucas.com.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.domain.usecase.GetCocktailDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailDetailViewModel(
    private val getCocktailByIdUseCase: GetCocktailDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailDetailUiState())
    val uiState: StateFlow<CocktailDetailUiState> = _uiState.asStateFlow()

    fun getCocktailById(id: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = getCocktailByIdUseCase(id)) {
                is OperationResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = null, cocktail = result.data)
                    }
                }

                is OperationResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.exception)
                    }
                }
            }
        }
    }
}

data class CocktailDetailUiState(
    val cocktail: CocktailModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)