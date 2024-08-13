package baccaro.lucas.com.data.remote.mapper

import baccaro.lucas.com.data.local.entity.CocktailEntity
import baccaro.lucas.com.data.remote.dto.CocktailDto
import baccaro.lucas.com.domain.model.CocktailModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
object CocktailMapper {
    fun mapToDomain(dto: CocktailDto): CocktailModel {
        return CocktailModel(
            id = dto.idDrink,
            name = dto.strDrink,
            ingredients = listOfNotNull(
                dto.strIngredient1,
                dto.strIngredient2,
                dto.strIngredient3,
                dto.strIngredient4
            ),
            isAlcoholic = dto.strAlcoholic == "Alcoholic",
            image = dto.strDrinkThumb ?: ""
        )
    }
    fun mapToDomain(entity: CocktailEntity): CocktailModel {
        return CocktailModel(
            id = entity.id,
            name = entity.name,
            image = entity.image,
            ingredients = Json.decodeFromString(entity.ingredients),
            isAlcoholic = entity.isAlcoholic
        )
    }
    fun mapToEntity(model: CocktailModel): CocktailEntity {
        return CocktailEntity(
            id = model.id,
            name = model.name,
            image = model.image,
            ingredients = Json.encodeToString(model.ingredients),
            isAlcoholic = model.isAlcoholic
        )
    }
}