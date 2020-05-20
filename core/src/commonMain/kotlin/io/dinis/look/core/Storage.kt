package io.dinis.look.core


/**
 * Created by Dinis Ishmukhametov on 20,May,2020
 * dinis.ish@gmail.com
 * Kazan, RUSSIA.
 */
interface Storage {
    fun remove(key: String)
    fun add(key: String, value: String)
    fun get(key: String) : List<String>
}