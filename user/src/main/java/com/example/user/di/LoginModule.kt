package com.example.user.di

import com.example.user.presentation.login.LoginViewState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class LoginModule {

    @Provides
    fun provideInitialLoginViewState(): LoginViewState = LoginViewState()
}
