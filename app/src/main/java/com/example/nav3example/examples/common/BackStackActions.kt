package com.example.nav3example.examples.common

fun <T> MutableList<T>.popIfNotRoot(): T? {
    if (size <= 1) return null
    return removeAt(lastIndex)
}
