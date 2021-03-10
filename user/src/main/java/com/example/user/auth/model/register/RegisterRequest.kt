package com.example.user.auth.model.register

import com.example.base.network.model.Data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    val `data`: Data
) {
    companion object {
        fun make(username: String, password: String): RegisterRequest {
            return RegisterRequest(
                    data = Data(
                            attributes = mapOf(ATTRIBUTE_EMAIL
                                    to username, ATTRIBUTE_PASSWORD to password),
                            type = TYPE_ACCOUNTS
                    )
            )
        }

        const val TYPE_ACCOUNTS = "accounts"
        const val ATTRIBUTE_EMAIL = "email"
        const val ATTRIBUTE_PASSWORD = "password"
    }
}