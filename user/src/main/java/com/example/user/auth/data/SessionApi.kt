package com.example.user.auth.data

import com.example.user.auth.model.Tokens
import com.example.user.auth.model.register.RegisterReq
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

internal interface SessionApi {
    @POST("loginUrl")
    fun login(username: String, password: String): Single<Tokens>

    @POST("auth/accounts")
    fun register(@Body registerReq: RegisterReq): Completable

    @POST("forgotPasswordUrl")
    fun forgotPassword(username: String): Completable
}
