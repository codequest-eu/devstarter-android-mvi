package com.example.user.auth.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Tokens(
    val accessToken: String,
    val refreshToken: String
)
