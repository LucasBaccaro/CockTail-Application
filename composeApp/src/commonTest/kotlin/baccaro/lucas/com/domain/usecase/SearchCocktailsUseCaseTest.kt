import baccaro.lucas.com.data.remote.api.OperationResult
import baccaro.lucas.com.data.remote.dto.CocktailDto
import baccaro.lucas.com.data.remote.mapper.CocktailMapper
import baccaro.lucas.com.domain.model.CocktailModel
import baccaro.lucas.com.domain.repository.CocktailRepository
import baccaro.lucas.com.domain.usecase.FakeCocktailRepository
import baccaro.lucas.com.domain.usecase.GetCocktailDetailUseCase
import baccaro.lucas.com.domain.usecase.SearchCocktailsUseCase
import baccaro.lucas.com.presentation.screen.home.CocktailSearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
class SearchCocktailsUseCaseTest {
    private lateinit var repository: FakeCocktailRepository
    private lateinit var useCase: SearchCocktailsUseCase

    @BeforeTest
    fun before() {
        repository = FakeCocktailRepository()
        useCase = SearchCocktailsUseCase(repository)
    }

    @AfterTest
    fun after() {
        // Limpieza si es necesaria
    }

    @Test
    fun `Verify successful search from API and correct mapping`() = runTest {
        val query = "Margarita"
        val apiCocktails = listOf(
            CocktailDto(
                idDrink = "1",
                strDrink = "Margarita",
                strAlcoholic = "Alcoholic",
                strIngredient1 = "Tequila",
                strIngredient2 = "Lime juice",
                strIngredient3 = "Triple sec",
                strIngredient4 = null,
                strDrinkThumb = "margarita.jpg",
                strInstructions = "Shake with ice",
                strGlass = "Margarita glass",
                strCategory = "Cocktail"
            )
        )
        repository.setApiCocktails(apiCocktails)

        val result = useCase(query)

        assertTrue(result is OperationResult.Success)
        val cocktails = (result as OperationResult.Success).data
        assertEquals(1, cocktails.size)
        assertEquals("1", cocktails[0].id)
        assertEquals("Margarita", cocktails[0].name)
        assertEquals(listOf("Tequila", "Lime juice", "Triple sec"), cocktails[0].ingredients)
        assertTrue(cocktails[0].isAlcoholic)
    }

    @Test
    fun `Verify search returns local data without calling API`() = runTest {
        val query = "Mojito"
        val localCocktails = listOf(
            CocktailModel(
                id = "2",
                name = "Mojito",
                ingredients = listOf("Rum", "Mint", "Lime"),
                isAlcoholic = true,
                image = "mojito.jpg",
                instructions = "Muddle and shake",
                glass = "Highball glass",
                category = "Cocktail"
            )
        )
        repository.setLocalCocktails(localCocktails)

        val result = useCase(query)

        assertTrue(result is OperationResult.Success)
        assertEquals(localCocktails, (result as OperationResult.Success).data)
        assertFalse(repository.wasApiCalled)
    }

    @Test
    fun `Verify error when no cocktails found`() = runTest {
        val query = "NonexistentCocktail"
        repository.setApiCocktails(null)

        val result = useCase(query)

        assertTrue(result is OperationResult.Error)
        assertEquals("No drinks found", (result as OperationResult.Error).exception)
    }

    @Test
    fun `Verify empty query returns error`() = runTest {
        val query = ""

        val result = useCase(query)

        assertTrue(result is OperationResult.Error)
        assertEquals("Query cannot be empty", (result as OperationResult.Error).exception)
    }
}