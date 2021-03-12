package com.example.base.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Relationship(
    val `data`: Data?
)
