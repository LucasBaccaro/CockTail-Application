package baccaro.lucas.com.domain.usecase

import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.domain.repository.CocktailRepository

class GetCocktailDetailUseCase(private val repository: CocktailRepository) {
    suspend operator fun invoke(id: String): OperationResult<CocktailModel> {
        return repository.getCocktailById(id)
    }
}