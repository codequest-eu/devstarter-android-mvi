package com.example.user.presentation.login

sealed class LoginViewEvent {
    object LoginFailed : LoginViewEvent()
    object LoginSuccess : LoginViewEvent()
}
