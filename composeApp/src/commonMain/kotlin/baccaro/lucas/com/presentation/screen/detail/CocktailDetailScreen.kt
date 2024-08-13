package baccaro.lucas.com.presentation.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import baccaro.lucas.com.presentation.screen.components.CenterText
import baccaro.lucas.com.presentation.screen.components.Loading

@Composable
fun CocktailDetailScreen(viewmodel: CocktailDetailViewModel, onBackClick: () -> Unit) {
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle().value

    when {
        uiState.isLoading -> Loading()
        uiState.error != null -> CenterText(uiState.error, Color.Red)
        else -> uiState.cocktail?.let { CocktailDetailContent(it, onBackClick = onBackClick) }
    }
}
