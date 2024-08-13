package baccaro.lucas.com.data.remote.repository

import baccaro.lucas.com.data.local.dao.CocktailDao
import baccaro.lucas.com.data.remote.api.CocktailApi
import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.data.remote.mapper.CocktailMapper
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.first
class CocktailRepositoryImpl(
    private val api: CocktailApi,
    private val dao: CocktailDao
) : CocktailRepository {
    override suspend fun searchCocktails(query: String): OperationResult<List<CocktailModel>> {

        val localCocktails = dao.searchCocktails(query).first()
        if (localCocktails.isNotEmpty()) {
            return OperationResult.Success(localCocktails.map { CocktailMapper.mapToDomain(it) })
        }

        return when (val response = api.searchCocktails(query)) {
            is OperationResult.Success -> {
                val drinks = response.data.drinks
                if (drinks != null) {
                    val cocktails = drinks.map { CocktailMapper.mapToDomain(it) }
                    dao.insertCocktails(cocktails.map { CocktailMapper.mapToEntity(it) })
                    OperationResult.Success(cocktails)
                } else {
                    OperationResult.Error("No drinks found")
                }
            }

            is OperationResult.Error -> response
        }
    }

    override suspend fun getCocktailById(id: String): OperationResult<CocktailModel> {
        val cocktailEntity = dao.getCocktailById(id)
        return if (cocktailEntity != null) {
            OperationResult.Success(CocktailMapper.mapToDomain(cocktailEntity))
        } else {
            OperationResult.Error("Cocktail not found locally")
        }
    }

    override suspend fun clearCache() {
        dao.deleteAllCocktails()
    }
}