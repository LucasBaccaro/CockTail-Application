package baccaro.lucas.com.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.NoDrinks
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import baccaro.lucas.com.domain.model.CocktailModel
import cocktail_application.composeapp.generated.resources.Res
import cocktail_application.composeapp.generated.resources.cocktail_category
import cocktail_application.composeapp.generated.resources.cocktail_glass
import cocktail_application.composeapp.generated.resources.cocktail_ingredients
import cocktail_application.composeapp.generated.resources.cocktail_instructions
import cocktail_application.composeapp.generated.resources.non_cocktail_instructions
import cocktail_application.composeapp.generated.resources.non_specified
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource

@Composable
fun CocktailDetailContent(cocktail: CocktailModel, modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Box {
            AsyncImage(
                model = cocktail.image,
                contentDescription = cocktail.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .size(20.dp)
                    .background(Color.Black.copy(alpha = 0.7f), CircleShape)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = cocktail.name,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (cocktail.isAlcoholic) Icons.Default.LocalBar else Icons.Default.NoDrinks,
                    contentDescription = if (cocktail.isAlcoholic) "Alcoholic" else "Non-Alcoholic",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (cocktail.isAlcoholic) "Alcoholic" else "Non-Alcoholic",
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.cocktail_ingredients),
            )
            cocktail.ingredients.forEach { ingredient ->
                Text("• $ingredient")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.cocktail_instructions),
            )
            Text(cocktail.instructions ?: stringResource(Res.string.non_cocktail_instructions))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.cocktail_glass),
            )
            Text(cocktail.glass ?: stringResource(Res.string.non_specified))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.cocktail_category),
            )
            Text(cocktail.category ?: stringResource(Res.string.non_specified))
        }
    }
}