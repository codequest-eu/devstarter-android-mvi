package com.example.user.auth.model.register

import com.example.base.network.Data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterReq(
    val `data`: Data
) {
    companion object {
        fun make(username: String, password: String): RegisterReq {
            return RegisterReq(
                    data = Data(
                            attributes = mapOf(ATTRIBUTE_EMIAL to username, ATTRIBUTE_PASSWORD to password),
                            type = TYPE_ACCOUNTS
                    )
            )
        }

        const val TYPE_ACCOUNTS = "accounts"
        const val ATTRIBUTE_EMIAL = "email"
        const val ATTRIBUTE_PASSWORD = "password"
    }
}