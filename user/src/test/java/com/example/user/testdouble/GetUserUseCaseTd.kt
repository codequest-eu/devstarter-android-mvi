package com.example.user.testdouble

import com.example.user.model.User
import com.example.user.usecase.GetUserUseCase
import io.reactivex.rxjava3.core.Single

internal class GetUserUseCaseTd : GetUserUseCase {

    var user: User? = User(DEFAULT_USER_NAME)

    override fun execute(): Single<User> {
        return user?.let { Single.just(it) } ?: Single.error(IllegalStateException("No user found"))
    }

    companion object {
        const val DEFAULT_USER_NAME = "TestUser"
    }
}
