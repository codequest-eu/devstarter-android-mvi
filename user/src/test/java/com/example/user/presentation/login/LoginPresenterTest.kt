package com.example.user.presentation.login

import com.example.base.testutils.TestSchedulersFactory
import com.example.base.utils.SchedulersFactory
import com.example.user.BaseTest
import com.example.user.presentation.login.LoginConstants.LOGGED_OUT_NAME
import com.example.user.testdouble.GetUserUseCaseTd
import org.junit.Before
import org.junit.Test

class LoginPresenterTest : BaseTest() {

    private val getUserUseCase = GetUserUseCaseTd()

    @Before
    fun setup() {
        SchedulersFactory.set(TestSchedulersFactory())
    }

    @Test
    fun `given logged out state, when login emits value then state contains logged in status and proper user name`() {
        // given
        val initialState = getWelcomeViewState()
        val mainPresenter = LoginPresenter(initialState, getUserUseCase)

        // when
        val testObserver = mainPresenter.viewState.test()
        mainPresenter.acceptIntent(LoginIntent.Login)

        // then
        testObserver.assertValueAt(0) {
            it.isLoggedIn == false
        }

        testObserver.assertValueAt(1) {
            it.name == GetUserUseCaseTd.DEFAULT_USER_NAME
        }
    }

    @Test
    fun `given logged in state, when logout emits value then new state contains logged out status and default text`() {
        // given
        val initialState = getLoggedViewState(GetUserUseCaseTd.DEFAULT_USER_NAME)
        val mainPresenter = LoginPresenter(initialState, getUserUseCase)

        // when
        val testObserver = mainPresenter.viewState.test()
        mainPresenter.acceptIntent(LoginIntent.Logout)

        // then
        testObserver.assertValueAt(0) {
            it.isLoggedIn == true
        }

        testObserver.assertValueAt(1) {
            it.isLoggedIn == false
        }
    }

    @Test
    fun `given WelcomeState, when LoginIntent accepted 3 times, then LoginSuccess ViewEvent emitted`() {
        // given
        val initialState = getWelcomeViewState()
        val mainPresenter = LoginPresenter(initialState, getUserUseCase)

        // when
        val testObserver = mainPresenter.viewEvents.test()

        mainPresenter.acceptIntent(LoginIntent.Login)
        mainPresenter.acceptIntent(LoginIntent.Login)
        mainPresenter.acceptIntent(LoginIntent.Login)

        // then
        testObserver.assertValueAt(testObserver.values().size - 1) {
            it is LoginViewEvent.LoginSuccess
        }
    }

    private fun getWelcomeViewState() = LoginViewState(LOGGED_OUT_NAME, false)
    private fun getLoggedViewState(userName: String) = LoginViewState(userName, true)
}
