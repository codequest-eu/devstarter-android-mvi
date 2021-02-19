package com.example.user.data

import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalUserRepository @Inject constructor() : UserRepository {
    override fun getUser(): Single<com.example.user.model.User> {
        return Single
                .just(com.example.user.model.User(LOCAL_USER_NAME))
                .subscribeOn(SchedulersFactory.io)
    }

    companion object {
        const val LOCAL_USER_NAME = "Local User"
    }
}
