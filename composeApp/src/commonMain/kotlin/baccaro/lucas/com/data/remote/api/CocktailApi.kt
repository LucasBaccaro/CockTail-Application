package baccaro.lucas.com.data.remote.api

import baccaro.lucas.com.data.remote.dto.CocktailResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CocktailApi(private val httpClient: HttpClient) {
    suspend fun searchCocktails(query: String): OperationResult<CocktailResponseDto> {
        return try {
            val response = httpClient.get("/api/json/v1/1//search.php") {
                parameter("s", query)
            }
            OperationResult.Success(response.body())
        } catch (e: Exception) {
            OperationResult.Error(e.message.toString())
        }
    }
}

sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val exception: String) : OperationResult<Nothing>()
}