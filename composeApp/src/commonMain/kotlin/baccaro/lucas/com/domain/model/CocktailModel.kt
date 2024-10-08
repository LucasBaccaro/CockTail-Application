package baccaro.lucas.com.domain.model
data class CocktailModel(
    val id: String,
    val name: String,
    val ingredients: List<String>,
    val isAlcoholic: Boolean,
    val image: String,
    val instructions: String?,
    val glass: String?,
    val category: String?
)