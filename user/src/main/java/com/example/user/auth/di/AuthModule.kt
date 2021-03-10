package com.example.user.auth.di

import android.content.SharedPreferences
import com.example.base.lib.PreferencesModule
import com.example.base.network.BaseRetrofitFactory
import com.example.base.network.NetworkModule
import com.example.user.auth.Consts
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
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal class AuthModule {

    @Provides
    fun provideAuthenticator(impl: UserAuthenticator): Authenticator = impl

    @Provides
    fun provideRefreshTokenApi(
            factory: AuthRetrofitFactory
    ): RefreshTokenApi {
        return factory.create(NetworkModule.BACKEND_URL, RefreshTokenApi::class.java)
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
    fun authOkHttpClient(
            @Named(AUTH_INTERCEPTOR_NAME) authInterceptor: Interceptor,
            authenticator: Authenticator,
            baseRetrofitFactory: BaseRetrofitFactory
    ): AuthRetrofitFactory {
        val retrofitFactory = baseRetrofitFactory.buildUpon {
            addNetworkInterceptor(authInterceptor)
            authenticator(authenticator)
        }

        return AuthRetrofitFactory(retrofitFactory)
    }

    @Provides
    fun provideSessionApi(
            factory: BaseRetrofitFactory
    ): SessionApi {
        return factory
                .create(NetworkModule.BACKEND_URL, SessionApi::class.java)
    }

    @Provides
    fun provideLoginUseCase(impl: LoginUseCaseImpl): LoginUserCase = impl

    @Provides
    fun provideRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase = impl

    companion object {
        private const val AUTH_INTERCEPTOR_NAME = "AUTH_INTERCEPTOR_NAME"
    }
}
