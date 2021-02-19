package com.example.user.auth.model.register

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterReq(
    val `data`: Data
) {
    companion object {
        fun make(username: String, password: String): RegisterReq {
            return RegisterReq(
                    data = Data(
                            attributes = Attributes(username, password),
                            type = TYPE_ACCOUNTS
                    )
            )
        }

        const val TYPE_ACCOUNTS = "accounts"
    }
}