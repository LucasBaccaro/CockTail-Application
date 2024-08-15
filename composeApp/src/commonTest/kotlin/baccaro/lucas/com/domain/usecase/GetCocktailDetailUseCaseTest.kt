package baccaro.lucas.com.domain.usecase

import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.domain.model.CocktailModel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCocktailDetailUseCaseTest {
    private lateinit var repository: FakeCocktailRepository
    private lateinit var useCase: GetCocktailDetailUseCase

    @BeforeTest
    fun before() {
        repository = FakeCocktailRepository()
        useCase = GetCocktailDetailUseCase(repository)
    }

    @AfterTest
    fun after() {
        // Limpieza si es necesaria
    }

    @Test
    fun `Verify successful retrieval from local storage`() = runTest {
        val cocktailId = "1"
        val expectedCocktail = CocktailModel(
            id = cocktailId,
            name = "Margarita",
            ingredients = listOf("Tequila", "Lime juice", "Triple sec"),
            isAlcoholic = true,
            image = "margarita.jpg",
            instructions = "Shake with ice and strain",
            glass = "Margarita glass",
            category = "Cocktail"
        )
        repository.setLocalCocktail(expectedCocktail)

        val result = useCase(cocktailId)

        assertTrue(result is OperationResult.Success)
        assertEquals(expectedCocktail, (result as OperationResult.Success).data)
    }

    @Test
    fun `Verify error when cocktail not found in local storage`() = runTest {
        val cocktailId = "999"

        val result = useCase(cocktailId)

        assertTrue(result is OperationResult.Error)
        assertEquals("Cocktail not found locally", (result as OperationResult.Error).exception)
    }
}