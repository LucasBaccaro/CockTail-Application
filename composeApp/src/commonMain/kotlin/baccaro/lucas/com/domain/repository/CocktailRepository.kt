package baccaro.lucas.com.domain.repository

import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.model.CocktailModel
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun searchCocktails(query: String): OperationResult<List<CocktailModel>>
    suspend fun clearCache()
}