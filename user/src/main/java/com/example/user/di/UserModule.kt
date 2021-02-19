package com.example.user.di

import com.example.base.network.NetworkModule
import com.example.user.auth.data.SessionApi
import com.example.user.auth.usecase.LoginUseCaseImpl
import com.example.user.auth.usecase.LoginUserCase
import com.example.user.data.LocalUserRepository
import com.example.user.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UserModule {

    @Provides
    @Singleton
    fun provideUserRepository(impl: LocalUserRepository): UserRepository = impl


}
