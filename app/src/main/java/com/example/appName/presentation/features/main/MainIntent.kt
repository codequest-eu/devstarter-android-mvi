package com.example.appName.presentation.features.main

sealed class MainIntent {
    object Login : MainIntent()
    object Logout : MainIntent()
    object Dummy: MainIntent()
}
