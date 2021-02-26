package com.example.user.presentation.register

import androidx.hilt.lifecycle.ViewModelInject
import com.example.base.presentation.BasePresenter
import com.example.user.auth.usecase.RegisterUseCase
import io.reactivex.rxjava3.core.Flowable

class RegisterPresenter @ViewModelInject constructor(
        initialState: RegisterViewState,
        private val registerUserCase: RegisterUseCase
) : BasePresenter<RegisterViewState, RegisterViewState.PartialState, RegisterIntent, RegisterViewEvent>(initialState) {

    override fun reduceViewState(previousState: RegisterViewState, partialState: RegisterViewState.PartialState): RegisterViewState {
        return when(partialState) {
            is RegisterViewState.PartialState.LoginSuccess -> RegisterViewState()
        }
    }

    override fun provideViewIntents(): Flowable<RegisterViewState.PartialState> {
        return intentProcessor
                .flatMap {
                    when (it) {
                        is RegisterIntent.Register -> triggerRegister(it)
                        is RegisterIntent.GoBack -> handleGoBack()
                    }
                }
    }

    private fun handleGoBack(): Flowable<RegisterViewState.PartialState> {
        publishEvent(RegisterViewEvent.GoBack)

        return Flowable.empty()
    }

    private fun triggerRegister(it: RegisterIntent.Register): Flowable<RegisterViewState.PartialState> {
        return registerUserCase.execute(it.username, it.password)
                .map {
                    RegisterViewState.PartialState.LoginSuccess as RegisterViewState.PartialState
                }
                .toFlowable()
    }
}
