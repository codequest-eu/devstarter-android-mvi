package com.example.user.auth.data

import com.example.user.auth.model.Tokens
import io.reactivex.rxjava3.core.Single

internal interface RefreshTokenApi {
    fun refresh(refreshToken: String): Single<Tokens>
}
