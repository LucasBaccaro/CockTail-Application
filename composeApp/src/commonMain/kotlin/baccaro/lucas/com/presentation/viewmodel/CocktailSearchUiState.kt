package baccaro.lucas.com.presentation.viewmodel

import baccaro.lucas.com.domain.model.CocktailModel
data class CocktailSearchUiState(
    val query: String = "",
    val cocktails: List<CocktailModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)