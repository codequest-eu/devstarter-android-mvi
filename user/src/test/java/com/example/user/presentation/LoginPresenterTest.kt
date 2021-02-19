package com.example.user.presentation

import com.example.base.testutils.TestSchedulersFactory
import com.example.base.utils.SchedulersFactory
import com.example.user.BaseTest
import com.example.user.data.ExampleUserRepository
import com.example.user.model.ExampleUser
import com.example.user.presentation.login.LoginConstants.LOGGED_OUT_NAME
import com.example.user.presentation.login.LoginIntent
import com.example.user.presentation.login.LoginPresenter
import com.example.user.presentation.login.LoginViewState
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test

class LoginPresenterTest : BaseTest() {
    private val exampleUserRepository: ExampleUserRepository = object : ExampleUserRepository {
        override fun getUser(): Single<ExampleUser> = Single.just(testUser)
    }

    private val testUser = ExampleUser("John")

    @Before
    fun setup() {
        SchedulersFactory.set(TestSchedulersFactory())
    }

    @Test
    fun `given logged out state, when login emits value then state contains logged in status and proper user name`() {
        // given
        val initialState = getWelcomeViewState()
        val mainPresenter = LoginPresenter(initialState, exampleUserRepository)

        // when
        val testObserver = mainPresenter.viewState.test()
        mainPresenter.acceptIntent(LoginIntent.Login)

        // then
        testObserver.assertValues(
            getWelcomeViewState(),
            getLoggedViewState(testUser.name)
        )
    }

    @Test
    fun `given logged in state, when logout emits value then new state contains logged out status and default text`() {
        // given
        val initialState = getLoggedViewState(testUser.name)
        val mainPresenter = LoginPresenter(initialState, exampleUserRepository)

        // when
        val testObserver = mainPresenter.viewState.test()
        mainPresenter.acceptIntent(LoginIntent.Logout)

        // then
        testObserver.assertValues(
            getLoggedViewState(testUser.name),
            getWelcomeViewState()
        )
    }

    private fun getWelcomeViewState() = LoginViewState(LOGGED_OUT_NAME, false)
    private fun getLoggedViewState(userName: String) = LoginViewState(userName, true)
}
