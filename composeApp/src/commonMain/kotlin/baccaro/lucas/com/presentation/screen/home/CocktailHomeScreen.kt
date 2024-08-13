package baccaro.lucas.com.presentation.screen.home

import SearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            uiState.isLoading -> CircularProgressIndicator()
            uiState.error != null -> uiState.error?.let { Text(it) }
            uiState.cocktails.isEmpty() -> Text("Vacios")
            else -> CocktailHomeContent(uiState.cocktails, onCocktailClick)
        }
    }
}