package io.dinis.look.core

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging

/**
 * Created by Dinis Ishmukhametov on 19,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */

actual fun platformName(): String {
    return "Android"
}

actual fun defaultSerializer(): JsonSerializer {
    return GsonSerializer()
}

actual fun client(cookiesStorage: Storage): HttpClient {
    return HttpClient(Android){
        install(JsonFeature){
            serializer = defaultSerializer()
        }
        install(HttpCookies) {
            storage = SimpleCookiesStorage(cookiesStorage)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}