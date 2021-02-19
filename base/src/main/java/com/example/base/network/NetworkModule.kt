package com.example.base.network

import com.example.base.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Named(CONTENTTYPE_INTERCEPTOR_NAME)
    fun contentTypeInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .header("Content-Type", "application/vnd.api+json")
                    .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Named(LOGGING_INTERCEPTOR_NAME)
    fun loggingInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            noopInterceptor()
        }
    }

    @Provides
    @Named(BASE_INTERCEPTORS_NAME)
    fun baseInterceptors(
            @Named(CONTENTTYPE_INTERCEPTOR_NAME) contentTypeInterceptor: Interceptor,
            @Named(LOGGING_INTERCEPTOR_NAME) loggingInterceptor: Interceptor
    ): MutableSet<Interceptor> = mutableSetOf(
            contentTypeInterceptor,
            loggingInterceptor
    )

    @Provides
    @Singleton
    @Named(BASE_OKHTTPCLIENT_NAME)
    fun authOkHttpClient(
            @Named(BASE_INTERCEPTORS_NAME) interceptors: MutableSet<Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            interceptors.forEach {
                addNetworkInterceptor(it)
            }
        }
                .build()
    }


    companion object {
        const val CONTENTTYPE_INTERCEPTOR_NAME = "CONTENTTYPE_INTERCEPTOR_NAME"
        const val LOGGING_INTERCEPTOR_NAME = "LOGGING_INTERCEPTOR_NAME"
        const val BASE_INTERCEPTORS_NAME = "BASE_INTERCEPTORS_NAME"
        const val BASE_OKHTTPCLIENT_NAME = "BASE_OKHTTPCLIENT_NAME"

        const val BACKEND_URL = "https://development-api.devstarter.codequest.dev/"
    }
}

fun noopInterceptor(): Interceptor {
    return Interceptor { chain ->
        chain.proceed(chain.request())
    }
}
