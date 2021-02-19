package com.example.user.auth.model.register

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
        val attributes: Attributes,
        val type: String
)