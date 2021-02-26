package com.example.user.presentation.register2

sealed class RegisterIntent() {
    data class Register(
            val username: String,
            val password: String
    ) : RegisterIntent()
}
