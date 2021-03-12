package com.example.user.auth.di

import com.example.base.network.RetrofitFactory

class AuthRetrofitFactory(retrofitFactory: RetrofitFactory) : RetrofitFactory by retrofitFactory
