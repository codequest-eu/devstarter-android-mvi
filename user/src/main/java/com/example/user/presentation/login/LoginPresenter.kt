package com.example.user.presentation.login

import androidx.hilt.lifecycle.ViewModelInject
import com.example.base.presentation.BasePresenter
import com.example.user.data.UserRepository
import com.example.user.presentation.login.LoginViewState.PartialState.LoggedInState
import com.example.user.presentation.login.LoginViewState.PartialState.WelcomeState
import io.reactivex.rxjava3.core.Flowable

internal class LoginPresenter @ViewModelInject constructor(
        initialState: LoginViewState,
        private val exampleUserRepository: UserRepository
) : BasePresenter<LoginViewState, LoginViewState.PartialState, LoginIntent, LoginViewEvent>(initialState) {

    override fun reduceViewState(previousState: LoginViewState, partialState: LoginViewState.PartialState): LoginViewState =
            when (partialState) {
                is WelcomeState ->
                    previousState.copy(name = partialState.loggedOutName, isLoggedIn = false)
                is LoggedInState ->
                    previousState.copy(name = partialState.loggedInName, isLoggedIn = true)
            }

    override fun provideViewIntents(): Flowable<LoginViewState.PartialState> =
            intentProcessor.flatMap {
                when (it) {
                    is LoginIntent.Login -> login()
                    LoginIntent.Logout -> logout()
                }
            }

    private fun logout(): Flowable<LoginViewState.PartialState> {
        return Flowable.just(WelcomeState(loggedOutName = LoginConstants.LOGGED_OUT_NAME))
    }

    private var loginCalls = 0
    private fun login(): Flowable<out LoginViewState.PartialState> {
        loginCalls += 1
        if (loginCalls == 2) {
            publishEvent(LoginViewEvent.LoginFailed)
            return Flowable.empty()
        }

        if (loginCalls % 3 == 0) {
            publishEvent(LoginViewEvent.LoginSuccess)
            return Flowable.empty()
        }

        return exampleUserRepository
                .getUser()
                .map { LoggedInState(it.name) }
                .toFlowable()
    }
}
