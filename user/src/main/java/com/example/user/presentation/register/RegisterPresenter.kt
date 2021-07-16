package com.example.user.presentation.register

import com.example.base.presentation.BasePresenter
import com.example.user.auth.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class RegisterPresenter @Inject constructor(
    initialState: RegisterViewState,
    private val registerUserCase: RegisterUseCase
) : BasePresenter<RegisterViewState, RegisterViewState.PartialState, RegisterIntent, RegisterViewEvent>(initialState) {

    override fun reduceViewState(previousState: RegisterViewState, partialState: RegisterViewState.PartialState): RegisterViewState {
        return when (partialState) {
            is RegisterViewState.PartialState.LoginSuccess -> RegisterViewState()
        }
    }

    override fun provideViewIntents(): Flowable<RegisterViewState.PartialState> {
        return intentProcessor
            .flatMap {
                when (it) {
                    is RegisterIntent.Register -> triggerRegister(it)
                    else -> Flowable.empty()
                }
            }
    }

    private fun triggerRegister(it: RegisterIntent.Register): Flowable<RegisterViewState.PartialState> {
        return registerUserCase.execute(it.username, it.password)
            .map {
                RegisterViewState.PartialState.LoginSuccess as RegisterViewState.PartialState
            }
            .toFlowable()
    }
}
