package baccaro.lucas.com.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import baccaro.lucas.com.data.local.entity.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails WHERE name LIKE '%' || :query || '%'")
    fun searchCocktails(query: String): Flow<List<CocktailEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(cocktails: List<CocktailEntity>)

    @Query("DELETE FROM cocktails")
    suspend fun deleteAllCocktails()
}

interface DB {
    fun clearAllTables()
}