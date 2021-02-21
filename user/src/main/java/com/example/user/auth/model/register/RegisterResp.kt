package com.example.user.auth.model.register

import com.example.base.network.Data
import com.example.user.auth.model.Tokens
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class RegisterResp(
        val `data`: Data,
        val included: List<Data>
)

private const val ATTRIBUTE_ACCESS_TOKEN = "access_token"
private const val ATTRIBUTE_REFRESH_TOKEN = "refresh_token"
private const val TYPE_SESSION = "session"

internal fun RegisterResp.toTokens(): Tokens? {
    val accessToken = this.included.find { it.type == TYPE_SESSION }?.attributes?.get(ATTRIBUTE_ACCESS_TOKEN)
    val refreshToken = this.included.find { it.type == TYPE_SESSION }?.attributes?.get(ATTRIBUTE_REFRESH_TOKEN)

    return if (accessToken != null && refreshToken != null) {
        Tokens(accessToken, refreshToken)
    } else {
        null
    }
}