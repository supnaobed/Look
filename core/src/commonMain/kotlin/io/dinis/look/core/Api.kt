package io.dinis.look.core

import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.cookies
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by Dinis Ishmukhametov on 20,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */
class Api(private val httpClient: HttpClient) {

    private val BASE_URL = "https://development.lookit.hk/api/test"
    private val CODE = "$BASE_URL/code"
    private val PING = "$BASE_URL/ping"
    private val PROFILE = "$BASE_URL/profile"

    suspend fun ping(): String {
        return httpClient.get(PING)
    }

    suspend fun sendCode(code: String): HttpResponse {
        return httpClient.post(CODE) {
            body = json.write(Code(code))
        }
    }

    suspend fun profile(): ServerResponse {
        val cookies = httpClient.cookies(PROFILE)
        if (cookies.isEmpty()){
            throw IllegalStateException("has not cookie")
        }
        return httpClient.get(PROFILE)
    }
}

@Serializable
data class Code(val code: String)

@Serializable
data class ServerResponse(
    val result: Result?
)

@Serializable
data class Result(
    @SerialName("_id")
    val id: String,
    val title: String
)