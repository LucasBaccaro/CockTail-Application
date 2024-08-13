package baccaro.lucas.com.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.usecase.SearchCocktailsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailSearchViewModel(
    private val searchCocktailsUseCase: SearchCocktailsUseCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val _uiState = MutableStateFlow(CocktailSearchUiState())
    val uiState: StateFlow<CocktailSearchUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            searchCocktails()
        }
    }

    private fun searchCocktails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            if (uiState.value.query != "") {
                when (val result = searchCocktailsUseCase(uiState.value.query)) {
                    is OperationResult.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = null, cocktails = result.data)
                        }
                    }

                    is OperationResult.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = result.exception)
                        }
                    }
                }
            }else{
                _uiState.update { it.copy(isLoading = false, error = null, cocktails = emptyList()) }
            }
        }
    }
}