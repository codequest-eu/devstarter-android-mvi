package com.example.user.presentation.register

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RegisterModule {
    @Provides
    fun provideInitialRegisterViewState(): RegisterViewState = RegisterViewState()
}
