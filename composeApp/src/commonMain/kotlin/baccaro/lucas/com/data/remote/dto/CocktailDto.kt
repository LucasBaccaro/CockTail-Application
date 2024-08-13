package baccaro.lucas.com.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class CocktailDto(
    @SerialName("idDrink") val idDrink: String,
    @SerialName("strDrink") val strDrink: String,
    @SerialName("strAlcoholic") val strAlcoholic: String,
    @SerialName("strIngredient1") val strIngredient1: String?,
    @SerialName("strIngredient2") val strIngredient2: String?,
    @SerialName("strIngredient3") val strIngredient3: String?,
    @SerialName("strIngredient4") val strIngredient4: String?,
    @SerialName("strDrinkThumb") val strDrinkThumb: String?,

)
@Serializable
data class CocktailResponseDto(
    @SerialName("drinks") val drinks: List<CocktailDto>?
)