package baccaro.lucas.com.domain.usecase

import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.data.remote.dto.CocktailDto
import baccaro.lucas.com.data.remote.mapper.CocktailMapper
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.domain.repository.CocktailRepository

class FakeCocktailRepository : CocktailRepository {
    private var localCocktails: List<CocktailModel> = emptyList()
    private var apiCocktails: List<CocktailDto>? = null
    var wasApiCalled: Boolean = false

    fun setLocalCocktails(cocktails: List<CocktailModel>) {
        localCocktails = cocktails
    }

    fun setApiCocktails(cocktails: List<CocktailDto>?) {
        apiCocktails = cocktails
    }

    fun setLocalCocktail(cocktail: CocktailModel) {
        localCocktails = listOf(cocktail)
    }

    override suspend fun searchCocktails(query: String): OperationResult<List<CocktailModel>> {
        if (query.isEmpty()) {
            return OperationResult.Error("Query cannot be empty")
        }

        val localResults = localCocktails.filter { it.name.contains(query, ignoreCase = true) }
        if (localResults.isNotEmpty()) {
            return OperationResult.Success(localResults)
        }

        wasApiCalled = true
        val apiResults = apiCocktails?.filter { it.strDrink.contains(query, ignoreCase = true) }
        return if (apiResults != null && apiResults.isNotEmpty()) {
            OperationResult.Success(apiResults.map { CocktailMapper.mapToDomain(it) })
        } else {
            OperationResult.Error("No drinks found")
        }
    }

    override suspend fun getCocktailById(id: String): OperationResult<CocktailModel> {
        val cocktail = localCocktails.find { it.id == id }
        return if (cocktail != null) {
            OperationResult.Success(cocktail)
        } else {
            OperationResult.Error("Cocktail not found locally")
        }
    }

    override suspend fun clearCache() {
        TODO("Not yet implemented")
    }
}