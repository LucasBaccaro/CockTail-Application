package baccaro.lucas.com.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import baccaro.lucas.com.presentation.screen.CocktailHomeScreen
import baccaro.lucas.com.presentation.viewmodel.CocktailDetailViewModel
import baccaro.lucas.com.presentation.viewmodel.CocktailSearchViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val cocktailViewModel: CocktailSearchViewModel = koinViewModel()
            CocktailHomeScreen(viewModel = cocktailViewModel, onCocktailClick = { cocktailId ->
                navController.navigate("detail/$cocktailId")
            })
        }

        composable(
            "detail/{cocktailId}",
            arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
        ) { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("cocktailId")
            val detailViewModel: CocktailDetailViewModel = koinViewModel()
            cocktailId?.let {
                detailViewModel.getCocktailById(it)
            }
            CocktailDetailScreen(detailViewModel)
        }
    }
}

@Composable
fun CocktailDetailScreen(detailViewModel: CocktailDetailViewModel) {
    val uiState = detailViewModel.uiState.collectAsStateWithLifecycle().value

    when {
        uiState.isLoading -> CircularProgressIndicator()
        uiState.error != null -> Text(uiState.error)
        else -> Text(uiState.cocktail.toString())
    }
}

@Serializable
object Home