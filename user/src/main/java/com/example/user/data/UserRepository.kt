package com.example.user.data

import io.reactivex.rxjava3.core.Single

internal interface UserRepository {
    fun getUser(): Single<com.example.user.model.User>
}
