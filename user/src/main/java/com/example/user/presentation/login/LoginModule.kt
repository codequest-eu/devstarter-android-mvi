package com.example.user.presentation.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal class LoginModule {

    @Provides
    fun provideInitialLoginViewState(): LoginViewState = LoginViewState()
}
