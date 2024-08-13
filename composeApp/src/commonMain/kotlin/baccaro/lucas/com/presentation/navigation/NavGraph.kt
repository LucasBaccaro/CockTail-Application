package baccaro.lucas.com.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.NoDrinks
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.presentation.screen.detail.CocktailDetailScreen
import baccaro.lucas.com.presentation.screen.home.CocktailHomeScreen
import baccaro.lucas.com.presentation.viewmodel.CocktailDetailViewModel
import baccaro.lucas.com.presentation.viewmodel.CocktailSearchViewModel
import coil3.compose.AsyncImage
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
            CocktailDetailScreen(detailViewModel)
        }
    }
}

@Serializable
object Home

@Serializable
data class Detail(val id: String)