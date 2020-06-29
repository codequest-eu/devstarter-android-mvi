package com.example.appName.presentation.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appName.data.model.ExampleUser
import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.appName.presentation.features.main.MainConstants.LOGGED_OUT_NAME
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainPresenterTest {
    @Rule
    @JvmField
    var testSchedulerRule = InstantTaskExecutorRule()

    private lateinit var exampleUserRepository: ExampleUserRepository
    private val testUser = ExampleUser("John")

    @Before
    fun setUp() {
        exampleUserRepository = object : ExampleUserRepository {
            override fun getUser(): Single<ExampleUser> = Single.just(testUser)
        }
    }

    @Test
    fun `given logged out state, when login emits value then state contains logged in status and proper user name`() {
        // given
        val initialState = getWelcomeViewState()
        val mainPresenter = MainPresenter(initialState, exampleUserRepository)
        val result = mutableListOf<MainViewState>()

        // when
        mainPresenter.stateObservable.observeForever { result.add(it) }
        mainPresenter.acceptIntent(MainIntent.Login)

        // then
        Assert.assertEquals(
                listOf(getWelcomeViewState(),
                        getLoggedViewState(testUser.name)),
                result)
    }

    @Test
    fun `given logged in state, when logout emits value then new state contains logged out status and default text`() {
        // given
        val initialState = getLoggedViewState(testUser.name)
        val mainPresenter = MainPresenter(initialState, exampleUserRepository)
        val result = mutableListOf<MainViewState>()

        // when
        mainPresenter.stateObservable.observeForever { result.add(it) }
        mainPresenter.acceptIntent(MainIntent.Logout)

        // then
        Assert.assertEquals(
                listOf(
                        getLoggedViewState(testUser.name),
                        getWelcomeViewState()
                ), result)
    }

    private fun getWelcomeViewState() = MainViewState(LOGGED_OUT_NAME, false)
    private fun getLoggedViewState(userName: String) = MainViewState(userName, true)
}
