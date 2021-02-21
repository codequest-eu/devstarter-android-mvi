package com.example.user.auth.di

import android.content.SharedPreferences
import com.example.base.lib.PreferencesModule
import com.example.base.network.NetworkModule
import com.example.user.auth.Consts
import com.example.user.auth.Consts.AUTH_INTERCEPTOR_NAME
import com.example.user.auth.Consts.AUTH_OKHTTP_CLIENT
import com.example.user.auth.data.*
import com.example.user.auth.usecase.LoginUseCaseImpl
import com.example.user.auth.usecase.LoginUserCase
import com.example.user.auth.usecase.RegisterUseCase
import com.example.user.auth.usecase.RegisterUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AuthModule {

    @Provides
    fun provideAuthenticator(impl: UserAuthenticator): Authenticator = impl

    @Provides
    fun authApi(
            @Named(AUTH_OKHTTP_CLIENT) okHttpClient: OkHttpClient
    ): RefreshTokenApi {
        return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(NetworkModule.BACKEND_URL)
                .build()
                .create(RefreshTokenApi::class.java)
    }

    @Provides
    fun authRepository(
            @Named(PreferencesModule.ENCRYPTED_PREFERENCES_NAME) encryptedPrefs: SharedPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(encryptedPrefs)
    }

    @Provides
    @Named(AUTH_INTERCEPTOR_NAME)
    fun authInterceptor(authRepository: AuthRepository): Interceptor {
        return Interceptor { chain ->
            val accessToken = authRepository.load()?.accessToken

            if (accessToken != null) {
                val builder = chain.request().newBuilder()
                val request = builder
                        .header(Consts.AUTHORIZATION_HEADER_KEY, Consts.AUTHORIZATION_HEADER_VALUE.format(accessToken))
                        .build()

                chain.proceed(request)
            } else {
                chain.proceed(chain.request())
            }
        }
    }

    @Provides
    @Singleton
    @Named(AUTH_OKHTTP_CLIENT)
    fun authOkHttpClient(
            @Named(AUTH_INTERCEPTOR_NAME) authInterceptor: Interceptor,
            @Named(NetworkModule.BASE_OKHTTPCLIENT_NAME) baseClient: OkHttpClient,
            authenticator: Authenticator
    ): OkHttpClient {
        return baseClient.newBuilder().apply {
            addNetworkInterceptor(authInterceptor)
            authenticator(authenticator)
        }
                .build()
    }

    @Provides
    fun provideSessionApi(
            @Named(NetworkModule.BASE_OKHTTPCLIENT_NAME) okHttpClient: OkHttpClient
    ): SessionApi {
        return Retrofit
                .Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(NetworkModule.BACKEND_URL)
                .build()
                .create(SessionApi::class.java)
    }

    @Provides
    fun provideLoginUseCase(impl: LoginUseCaseImpl): LoginUserCase = impl

    @Provides
    fun provideRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase = impl

    
}
