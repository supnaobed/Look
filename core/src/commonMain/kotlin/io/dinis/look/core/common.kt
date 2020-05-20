package io.dinis.look.core

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonSerializer


/**
 * Created by Dinis Ishmukhametov on 19,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */


expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks on ${platformName()}"
}

expect fun defaultSerializer(): JsonSerializer

expect fun client(cookiesStorage: Storage): HttpClient