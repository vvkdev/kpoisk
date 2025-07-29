package com.vvkdev.data.repositories.base

import com.vvkdev.core.AppDispatchers
import com.vvkdev.core.extensions.childScope
import com.vvkdev.data.remote.models.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Response

abstract class BaseRepository(
    protected val json: Json,
    private val appScope: CoroutineScope,
    private val appDispatchers: AppDispatchers,
) {

    protected inline fun <T> safeApiCall(call: () -> Response<T>): Result<T> =
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                val errorMessage = response.errorBody()?.string()
                    ?.let { json.decodeFromString<ErrorResponse>(it).messages }
                    ?.joinToString("\n")
                    ?: "Empty error body"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    protected fun launchDbOperation(block: suspend () -> Unit) {
        appScope.childScope(appDispatchers.io).launch { block() }
    }
}
