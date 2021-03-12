package com.example.user.auth.usecase

import com.example.base.utils.SchedulersFactory
import com.example.user.auth.data.AuthRepository
import com.example.user.auth.data.SessionApi
import com.example.user.auth.model.register.RegisterRequest
import com.example.user.auth.model.register.toTokens
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface RegisterUseCase {
    sealed class Result {
        object Success : Result()
        object Failure : Result()
    }

    fun execute(username: String, password: String): Single<Result>
}

internal class RegisterUseCaseImpl @Inject constructor(
    private val sessionApi: SessionApi,
    private val authRepository: AuthRepository
) : RegisterUseCase {
    override fun execute(username: String, password: String): Single<RegisterUseCase.Result> {
        return sessionApi
            .register(RegisterRequest.make(username, password))
            .subscribeOn(SchedulersFactory.io)
            .doOnSuccess {
                it.toTokens()?.let { t ->
                    authRepository.store(t)
                }
            }
            .map {
                RegisterUseCase.Result.Success as RegisterUseCase.Result
            }
            .onErrorReturn {
                RegisterUseCase.Result.Failure
            }
    }
}
