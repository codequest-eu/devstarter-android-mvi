package com.example.base.network

import com.squareup.moshi.Moshi
import moe.banana.jsonapi2.JsonApiConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class BaseRetrofitFactory(
        private val okHttpClient: OkHttpClient,
        private val moshi: Moshi
        ) :
        RetrofitFactory by retrofitFactory(okHttpClient, moshi) {

    fun buildUpon(block: OkHttpClient.Builder.() -> Unit): RetrofitFactory {
        val client = okHttpClient
                .newBuilder()
                .apply(block)
                .build()

        return retrofitFactory(client, moshi)
    }
}

private fun retrofitFactory(okHttpClient: OkHttpClient, moshi: Moshi): RetrofitFactory {
    return object : RetrofitFactory {
        override fun <T> create(baseUrl: String, clazz: Class<T>): T {
            return Retrofit
                    .Builder()
                    .client(okHttpClient)
                    .addConverterFactory(JsonApiConverterFactory.create(moshi))
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                    .create(clazz)
        }
    }
}
