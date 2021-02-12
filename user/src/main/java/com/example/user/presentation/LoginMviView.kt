package com.example.user.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.base.presentation.BaseMviView
import com.example.user.R

class LoginMviView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        override val acceptIntent: (LoginIntent) -> Unit
) : BaseMviView<LoginViewState, LoginIntent>() {
    override val rootView = layoutInflater.inflate(R.layout.fragment_login, parent, false)

    val mainText: TextView = rootView.findViewById(R.id.mainText)
    val loginButton: Button = rootView.findViewById(R.id.loginButton)
    val logoutButton: Button = rootView.findViewById(R.id.logoutButton)

    init {
        loginButton.setOnClickListener {
            acceptIntent(LoginIntent.Login)
        }

        logoutButton.setOnClickListener {
            acceptIntent(LoginIntent.Logout)
        }
    }

    override fun render(viewState: LoginViewState) {
        renderText(viewState)
        renderButtonsVisibility(viewState)
    }

    private fun renderText(viewState: LoginViewState) {
        mainText.text = rootView.context.getString(R.string.main_welcome_text, viewState.name)
    }

    private fun renderButtonsVisibility(viewState: LoginViewState) {
        if (viewState.isLoggedIn) {
            loginButton.visibility = View.GONE
            logoutButton.visibility = View.VISIBLE
        } else {
            loginButton.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE
        }
    }
}
