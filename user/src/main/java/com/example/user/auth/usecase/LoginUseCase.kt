package com.example.user.auth.usecase

import com.example.base.utils.SchedulersFactory
import com.example.user.auth.data.AuthRepository
import com.example.user.auth.data.SessionApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface LoginUserCase {
    sealed class Result {
        object Success : Result()
    }

    fun execute(username: String, password: String): Single<Result>
}

internal class LoginUseCaseImpl @Inject constructor(
    private val sessionApi: SessionApi,
    private val authRepository: AuthRepository
) : LoginUserCase {

    override fun execute(username: String, password: String): Single<LoginUserCase.Result> {
        return sessionApi
            .login(username, password)
            .subscribeOn(SchedulersFactory.io)
            .map {
                authRepository.store(it)
                LoginUserCase.Result.Success
            }
    }
}
