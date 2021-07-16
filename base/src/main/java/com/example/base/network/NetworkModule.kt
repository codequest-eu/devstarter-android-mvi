package com.example.base.network

import com.example.base.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import moe.banana.jsonapi2.Resource
import moe.banana.jsonapi2.ResourceAdapterFactory
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
    @Singleton
    fun baseOkHttpClient(
        @Named(CONTENTTYPE_INTERCEPTOR_NAME) contentTypeInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR_NAME) loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(contentTypeInterceptor)
            addNetworkInterceptor(loggingInterceptor)
        }
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(clazzes: MutableSet<Class<out Resource>>): Moshi {

        val builder = ResourceAdapterFactory
                .builder()

        clazzes.forEach {
            builder.add(it)
        }

        return Moshi
                .Builder()
                .add(builder.build())
                .build()
    }

    @Provides
    fun provideBaseRetrofitFactory(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): BaseRetrofitFactory {
        return BaseRetrofitFactory(okHttpClient, moshi)
    }

    companion object {
        private const val CONTENTTYPE_INTERCEPTOR_NAME = "CONTENTTYPE_INTERCEPTOR_NAME"
        private const val LOGGING_INTERCEPTOR_NAME = "LOGGING_INTERCEPTOR_NAME"

        const val JSONAPI_ADAPTER_SET = "JSONAPI_ADAPTER_SET"

        const val BACKEND_URL = "https://development-api.devstarter.codequest.dev/"
    }
}

fun noopInterceptor(): Interceptor {
    return Interceptor { chain ->
        chain.proceed(chain.request())
    }
}
