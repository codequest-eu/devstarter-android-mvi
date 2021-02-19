package com.example.user.data

import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun getUser(): Single<com.example.user.model.User>
}
