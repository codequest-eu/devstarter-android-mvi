package com.example.user.presentation.register

sealed class RegisterIntent() {
    data class Register(
        val username: String,
        val password: String
    ) : RegisterIntent()

    object GoBack : RegisterIntent()
}
