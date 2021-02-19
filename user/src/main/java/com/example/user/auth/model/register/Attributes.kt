package com.example.user.auth.model.register

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attributes(
    val email: String,
    val password: String
)