package com.example.base.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val attributes: Map<String, String>? = null,
    val type: String? = null,
    val id: String? = null,
    val relationships: Map<String, Relationship>? = null
)
