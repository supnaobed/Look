package io.dinis.look.core


import io.ktor.client.features.cookies.CookiesStorage
import io.ktor.http.*
import io.ktor.http.parseServerSetCookieHeader


/**
 * [CookiesStorage] that stores all the cookies in an in-memory map.
 */
class SimpleCookiesStorage(private val storage: Storage) : CookiesStorage {

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        with(cookie) {
            if (name.isBlank()) return
        }
        get(requestUrl).firstOrNull { it.name == cookie.name }?.let {
            storage.remove(requestUrl.host)
        }
        storage.add(requestUrl.host, renderSetCookieHeader(cookie))
    }

    override fun close() {

    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        val data = storage.get(requestUrl.host)
        if (data == null){
            return emptyList()
        }else{
            return data.map { parseServerSetCookieHeader(it) }
        }
    }

}