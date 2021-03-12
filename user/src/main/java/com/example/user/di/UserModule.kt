package com.example.user.di

import com.example.user.data.LocalUserRepository
import com.example.user.data.UserRepository
import com.example.user.usecase.GetUseUseCaseImpl
import com.example.user.usecase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UserModule {
    @Provides
    @Singleton
    fun provideUserRepository(impl: LocalUserRepository): UserRepository = impl

    @Provides
    fun provideGetUserUseCase(impl: GetUseUseCaseImpl): GetUserUseCase = impl
}
