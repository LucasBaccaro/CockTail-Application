package baccaro.lucas.com.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import baccaro.lucas.com.presentation.screen.CocktailHomeScreen
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
        startDestination = Home
    ) {
        composable<Home> {
            val cocktailViewModel: CocktailSearchViewModel = koinViewModel()
            CocktailHomeScreen(viewModel = cocktailViewModel)
        }
    }
}

@Serializable
object Home