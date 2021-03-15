package com.example.user.auth.data.dto

import com.example.user.auth.model.Tokens
import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "account")
internal data class AccountDto(
        val email: String = ""
) : Resource() {
    val session: HasOne<SessionDto> = HasOne()
    val user: HasOne<UserDto> = HasOne()

    fun getTokens(): Tokens = session.get(document).let {
        Tokens(it.access_token, it.refresh_token)
    }
}