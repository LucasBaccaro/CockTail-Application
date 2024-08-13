package baccaro.lucas.com.presentation.screen.home

import SearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import baccaro.lucas.com.presentation.screen.components.CenterText
import baccaro.lucas.com.presentation.screen.components.Loading
import cocktail_application.composeapp.generated.resources.Res
import cocktail_application.composeapp.generated.resources.cocktail_empty
import org.jetbrains.compose.resources.stringResource

@Composable
fun CocktailHomeScreen(viewModel: CocktailSearchViewModel, onCocktailClick: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        SearchBar(
            query = uiState.query,
            onQueryChange = { viewModel.onQueryChange(it) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        when {
            uiState.isLoading -> Loading()
            uiState.error != null -> uiState.error?.let { CenterText(it, Color.Red) }
            uiState.cocktails.isEmpty() -> CenterText(
                text = stringResource(Res.string.cocktail_empty),
                color = Color.Black
            )

            else -> CocktailHomeContent(uiState.cocktails, onCocktailClick)
        }
    }
}