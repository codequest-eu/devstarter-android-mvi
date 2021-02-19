package com.example.user.auth.data

import android.content.SharedPreferences
import com.example.user.auth.model.Tokens

internal class AuthRepositoryImpl(
        private val encryptedPrefs: SharedPreferences
): AuthRepository {
    override fun store(tokens: Tokens) {
    }

    override fun load(): Tokens? {
        return null
    }
}
