package com.example.user.auth.data.dto

import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "user")
data class UserDto(
        val first_name: String = "",
        val last_name: String = ""
) : Resource()