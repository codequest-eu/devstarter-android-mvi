package com.example.user.data

import com.example.base.utils.SchedulersFactory
import com.example.user.model.ExampleUser
import io.reactivex.rxjava3.core.Single

class LocalExampleUserRepository : ExampleUserRepository {
    override fun getUser(): Single<ExampleUser> {
        return Single
            .just(ExampleUser(LOCAL_USER_NAME))
            .subscribeOn(SchedulersFactory.io)
    }

    companion object {
        const val LOCAL_USER_NAME = "Local User"
    }
}
