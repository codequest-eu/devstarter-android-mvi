package com.example.user.data

import com.example.base.utils.SchedulersFactory
import com.example.user.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class LocalUserRepository @Inject constructor() : UserRepository {
    override fun getUser(): Single<User> {
        return Single
            .just(User(LOCAL_USER_NAME))
            .subscribeOn(SchedulersFactory.io)
    }

    companion object {
        const val LOCAL_USER_NAME = "Local User"
    }
}
