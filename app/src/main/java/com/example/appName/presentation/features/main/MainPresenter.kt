package com.example.appName.presentation.features.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.appName.presentation.features.base.BasePresenter
import com.example.appName.presentation.features.main.MainViewState.PartialState.LoggedInState
import com.example.appName.presentation.features.main.MainViewState.PartialState.WelcomeState
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

class MainPresenter @ViewModelInject constructor(
        initialState: MainViewState,
        private val exampleUserRepository: ExampleUserRepository
) : BasePresenter<MainViewState, MainViewState.PartialState, MainIntent>(initialState) {

    companion object {
        const val T = "BUG MAIN"
    }

    override fun reduceViewState(previousState: MainViewState, partialState: MainViewState.PartialState): MainViewState {
        Log.d(T, "${Thread.currentThread().name} reduceViewState")
        return when (partialState) {
            is WelcomeState ->
                previousState.copy(name = partialState.loggedOutName, isLoggedIn = false)
            is LoggedInState ->
                previousState.copy(name = partialState.loggedInName, isLoggedIn = true)
            is MainViewState.PartialState.Dummy -> previousState
        }
    }


    override fun provideViewIntents(): Flowable<MainViewState.PartialState> {
        Log.d(T, "${Thread.currentThread().name} provideViewIntents")
        return intentProcessor.flatMap {
            Log.d(T, "${Thread.currentThread().name} provideViewIntents intentProcessor.flatMap")
            when (it) {
                is MainIntent.Login -> login()
                MainIntent.Logout -> logout()
                MainIntent.Dummy -> dummy()
            }
        }
    }


    private fun dummy(): Flowable<MainViewState.PartialState> = Flowable
            .fromCallable {
                Log.d(T, "${Thread.currentThread().name} dummy callable")
                MainViewState.PartialState.Dummy() as MainViewState.PartialState
            }
            //BUG ctrl
            //.subscribeOn(Schedulers.io())



    private fun logout(): Flowable<MainViewState.PartialState> =
            Flowable.just(WelcomeState(loggedOutName = MainConstants.LOGGED_OUT_NAME))

    private fun login() =
            exampleUserRepository
                    .getUser()
                    .map { LoggedInState(it.name) }
                    .toFlowable()
}
