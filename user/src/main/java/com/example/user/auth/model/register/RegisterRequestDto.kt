package com.example.user.auth.model.register

import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "accounts")
data class RegisterRequestDto(
        val email: String = "",
        val password: String = ""
) : Resource() {

}
