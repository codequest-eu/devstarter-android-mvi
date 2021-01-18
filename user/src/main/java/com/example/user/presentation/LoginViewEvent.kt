package com.example.user.presentation

import com.example.base.nav.Direction

sealed class LoginViewEvent {
    object LoginFailed: LoginViewEvent()
    data class Navigate(val destination: Direction): LoginViewEvent()
}
