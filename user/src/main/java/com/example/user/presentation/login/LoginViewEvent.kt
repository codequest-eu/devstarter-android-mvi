package com.example.user.presentation.login

internal sealed class LoginViewEvent {
    object LoginFailed : LoginViewEvent()
    object LoginSuccess : LoginViewEvent()
}
