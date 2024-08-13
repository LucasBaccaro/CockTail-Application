package baccaro.lucas.com.presentation.screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.NoDrinks
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baccaro.lucas.com.domain.model.CocktailModel
import cocktail_application.composeapp.generated.resources.Res
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource

@Composable
fun CocktailGrid(cocktails: List<CocktailModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(cocktails) { cocktail ->
            CocktailItem(cocktail)
        }
    }
}

@Composable
fun CocktailItem(cocktail: CocktailModel) {
    Card(
        modifier = Modifier
            .height(280.dp)
            .padding(8.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = cocktail.image,
                contentDescription = cocktail.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = cocktail.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Ingredients",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
                CocktailIngredients(cocktail.ingredients)
            }
            AlcoholicIcon(cocktail.isAlcoholic)
        }
    }
}

@Composable
fun CocktailIngredients(ingredients: List<String>) {
    ingredients.chunked(2).forEach { pair ->
        Row(Modifier.padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            pair.forEach { ingredient ->
                Text(
                    text = "· $ingredient",
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun AlcoholicIcon(isAlcoholic: Boolean) {
    Icon(
        imageVector = if (isAlcoholic) Icons.Default.LocalBar else Icons.Default.NoDrinks,
        contentDescription = if (isAlcoholic) "Alcoholic" else "Non-Alcoholic",
        modifier = Modifier
            .padding(8.dp)
    )
}