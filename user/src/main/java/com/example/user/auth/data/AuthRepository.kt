package com.example.user.auth.data

import com.example.user.auth.model.Tokens

internal interface AuthRepository {
    fun store(tokens: Tokens)
    fun load(): Tokens?
}
