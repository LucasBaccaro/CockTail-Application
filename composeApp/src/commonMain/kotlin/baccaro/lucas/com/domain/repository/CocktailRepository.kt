package baccaro.lucas.com.domain.repository

import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.model.CocktailModel

interface CocktailRepository {
    suspend fun searchCocktails(query: String): OperationResult<List<CocktailModel>>
    suspend fun getCocktailById(id: String): OperationResult<CocktailModel>
    suspend fun clearCache()
}