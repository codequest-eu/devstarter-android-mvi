package com.example.base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class BaseRetrofitFactory(private val okHttpClient: OkHttpClient) :
    RetrofitFactory by okHttpClient.retrofitFactory() {

    fun buildUpon(block: OkHttpClient.Builder.() -> Unit): RetrofitFactory {
        val client = okHttpClient
            .newBuilder()
            .apply(block)
            .build()

        return client.retrofitFactory()
    }
}

private fun OkHttpClient.retrofitFactory(): RetrofitFactory {
    val okHttpClient = this
    return object : RetrofitFactory {
        override fun <T> create(baseUrl: String, clazz: Class<T>): T {
            return Retrofit
                .Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(clazz)
        }
    }
}
