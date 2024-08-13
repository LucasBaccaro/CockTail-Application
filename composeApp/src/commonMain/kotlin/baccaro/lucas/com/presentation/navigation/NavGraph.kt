package baccaro.lucas.com.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import baccaro.lucas.com.presentation.screen.detail.CocktailDetailScreen
import baccaro.lucas.com.presentation.screen.home.CocktailHomeScreen
import baccaro.lucas.com.presentation.screen.detail.CocktailDetailViewModel
import baccaro.lucas.com.presentation.screen.home.CocktailSearchViewModel
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
            CocktailHomeScreen(
                viewModel = cocktailViewModel,
                onCocktailClick = { cocktailId -> navController.navigate(Detail(cocktailId)) })
        }

        composable<Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<Detail>()
            val detailViewModel: CocktailDetailViewModel = koinViewModel()
            detailViewModel.getCocktailById(args.id)
            CocktailDetailScreen(detailViewModel, onBackClick = { navController.popBackStack() })
        }
    }
}

@Serializable
object Home

@Serializable
data class Detail(val id: String)