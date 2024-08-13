package baccaro.lucas.com.domain.usecase

import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.domain.repository.CocktailRepository
class SearchCocktailsUseCase(private val repository: CocktailRepository) {
    suspend operator fun invoke(query: String): OperationResult<List<CocktailModel>> {
        return repository.searchCocktails(query)
    }
    suspend fun clearCache() {
        repository.clearCache()
    }
}