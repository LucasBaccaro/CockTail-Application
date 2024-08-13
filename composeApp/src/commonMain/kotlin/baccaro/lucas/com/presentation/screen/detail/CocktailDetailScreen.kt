package baccaro.lucas.com.presentation.screen.detail

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import baccaro.lucas.com.presentation.viewmodel.CocktailDetailViewModel

@Composable
fun CocktailDetailScreen(detailViewModel: CocktailDetailViewModel, onBackClick: () -> Unit) {
    val uiState = detailViewModel.uiState.collectAsStateWithLifecycle().value

    when {
        uiState.isLoading -> CircularProgressIndicator()
        uiState.error != null -> Text(uiState.error)
        else -> uiState.cocktail?.let { CocktailDetailContent(it, onBackClick = onBackClick) }
    }
}
