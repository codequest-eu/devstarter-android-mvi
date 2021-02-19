package com.example.user.presentation.login

import com.example.base.nav.Direction

sealed class LoginViewEvent {
    object LoginFailed: LoginViewEvent()
    data class Navigate(val destination: Direction): LoginViewEvent()
}
