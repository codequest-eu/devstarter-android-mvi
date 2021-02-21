package com.example.user.auth.model.register

import com.example.base.network.Data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResp(
        val `data`: Data,
        val included: List<Data>
)