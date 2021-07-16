package com.example.user.auth.data.dto

import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "session")
data class SessionDto(
        val access_token: String = "",
        val refresh_token: String = ""
) : Resource()