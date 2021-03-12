package com.example.user.usecase

import com.example.user.data.UserRepository
import com.example.user.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface GetUserUseCase {
    fun execute(): Single<User>
}

internal class GetUseUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserUseCase {
    override fun execute(): Single<User> {
        return userRepository
            .getUser()
    }
}
