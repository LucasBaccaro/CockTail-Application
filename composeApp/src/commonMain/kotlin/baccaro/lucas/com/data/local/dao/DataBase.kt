package baccaro.lucas.com.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import baccaro.lucas.com.data.local.entity.CocktailEntity

@Database(entities = [CocktailEntity::class], version = 1,exportSchema = true)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun getDao(): CocktailDao
    override fun clearAllTables() {}
}