package com.example.user.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ExampleUserModule {

    @Provides
    @Singleton
    fun provideExampleUserRepository(): ExampleUserRepository = LocalExampleUserRepository()
}
