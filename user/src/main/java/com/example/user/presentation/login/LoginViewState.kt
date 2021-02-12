package com.example.user.presentation.login

import com.example.user.presentation.login.LoginConstants.LOGGED_OUT_NAME
import java.io.Serializable

data class LoginViewState(
        val name: String = LOGGED_OUT_NAME,
        val isLoggedIn: Boolean = false
) : Serializable {
    sealed class PartialState {
        data class WelcomeState(val loggedOutName: String) : PartialState()
        data class LoggedInState(val loggedInName: String) : PartialState()
    }
}

