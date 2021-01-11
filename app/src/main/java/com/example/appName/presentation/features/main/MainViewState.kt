package com.example.appName.presentation.features.main

import com.example.appName.presentation.features.main.MainConstants.LOGGED_OUT_NAME
import java.io.Serializable

data class MainViewState(
        val name: String = LOGGED_OUT_NAME,
        val isLoggedIn: Boolean = false
) : Serializable {
    sealed class PartialState {
        data class WelcomeState(val loggedOutName: String) : PartialState()
        data class LoggedInState(val loggedInName: String) : PartialState()
        data class Dummy(val foo: String = "bar"): PartialState()
    }
}
