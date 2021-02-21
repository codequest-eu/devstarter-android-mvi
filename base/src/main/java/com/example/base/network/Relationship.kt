package com.example.base.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Relationship(
        val `data`: Data?
)