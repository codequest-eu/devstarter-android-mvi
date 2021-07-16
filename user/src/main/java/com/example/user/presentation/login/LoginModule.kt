package com.example.user.presentation.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class LoginModule {

    @Provides
    fun provideInitialLoginViewState(): LoginViewState = LoginViewState()
}
