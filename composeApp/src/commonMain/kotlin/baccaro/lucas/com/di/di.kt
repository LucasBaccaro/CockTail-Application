import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import baccaro.lucas.com.data.local.dao.AppDatabase
import baccaro.lucas.com.data.local.dao.CocktailDao
import baccaro.lucas.com.data.remote.api.CocktailApi
import baccaro.lucas.com.data.remote.repository.CocktailRepositoryImpl
import baccaro.lucas.com.domain.repository.CocktailRepository
import baccaro.lucas.com.domain.usecase.GetCocktailByIdUseCase
import baccaro.lucas.com.domain.usecase.SearchCocktailsUseCase
import baccaro.lucas.com.presentation.screen.detail.CocktailDetailViewModel
import baccaro.lucas.com.presentation.screen.home.CocktailSearchViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
@OptIn(ExperimentalSerializationApi::class)
val appModule = module {
    single<CocktailDao> {
        val dbBuilder = get<RoomDatabase.Builder<AppDatabase>>()
        dbBuilder.setDriver(BundledSQLiteDriver()).build().getDao()
    }
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    useAlternativeNames = false
                })
            }
            defaultRequest {
                header("Accept", "application/json")
                url {
                    protocol = URLProtocol.HTTPS
                    host = "www.thecocktaildb.com"
                }
            }
        }
    }
    single { CocktailApi(get()) }
    single<CocktailRepository> { CocktailRepositoryImpl(get(), get()) }
    factoryOf(::SearchCocktailsUseCase)
    viewModelOf(::CocktailSearchViewModel)
    factoryOf(::GetCocktailByIdUseCase)
    viewModelOf(::CocktailDetailViewModel)
}
expect val nativeModule: Module
fun initKoin(config: KoinAppDeclaration? = null) = run {
    startKoin {
        config?.invoke(this)
        modules(appModule, nativeModule)
    }
}