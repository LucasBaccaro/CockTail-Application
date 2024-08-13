package baccaro.lucas.com.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: String,
    val ingredients: String,
    val isAlcoholic: Boolean
)