package com.example.user.presentation.register2

import java.io.Serializable

class RegisterViewState() : Serializable {
    sealed class PartialState {
        object LoginSuccess: PartialState()
    }
}
