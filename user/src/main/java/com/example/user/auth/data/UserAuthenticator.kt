package com.example.user.auth.data

import com.example.user.auth.Consts
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


internal class UserAuthenticator @Inject constructor(
        private val refreshTokenApi: RefreshTokenApi,
        private val authRepository: AuthRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            if (response.request.header(Consts.AUTHORIZATION_HEADER_KEY) != null) {

                val refreshToken = authRepository.load()?.refreshToken

                if (refreshToken != null) {
                    val r = refreshTokenApi.refresh(refreshToken).blockingGet()
                    authRepository.store(r)
                }
            }
        } catch (e: Exception) {
            return null
        }

        return response
                .request
                .newBuilder()
                .header(Consts.AUTHORIZATION_HEADER_KEY, Consts.AUTHORIZATION_HEADER_VALUE.format(authRepository.load()?.accessToken))
                .build()

    }
}
