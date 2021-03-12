package com.example.base.network

interface RetrofitFactory {
    fun <T> create(baseUrl: String, clazz: Class<T>): T
}
