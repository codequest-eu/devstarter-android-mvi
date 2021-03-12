package com.example.user.auth.data

import android.content.SharedPreferences
import com.example.user.auth.model.Tokens
import com.squareup.moshi.Moshi

internal class AuthRepositoryImpl(
    private val encryptedPrefs: SharedPreferences
) : AuthRepository {

    private val adapter = Moshi.Builder().build().adapter(Tokens::class.java)

    override fun store(tokens: Tokens) {
        val json = adapter.toJson(tokens)
        encryptedPrefs
            .edit()
            .putString(TOKENS_KEY, json)
            .apply()
    }

    override fun load(): Tokens? {
        val json = encryptedPrefs.getString(TOKENS_KEY, null)
        return json?.let { adapter.fromJson(it) }
    }

    companion object {
        private const val TOKENS_KEY = "TOKENS_KEY"
    }
}
