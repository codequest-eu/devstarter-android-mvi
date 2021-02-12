package com.example.user.presentation.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.base.presentation.BaseMviView
import com.example.user.R
import com.example.user.databinding.FragmentLoginBinding

internal class LoginMviView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        override val acceptIntent: (LoginIntent) -> Unit
) : BaseMviView<LoginViewState, LoginIntent>() {
    private val binding = FragmentLoginBinding.inflate(layoutInflater, parent, false)
    override val rootView = binding.root

    init {
        binding.loginButton.setOnClickListener {
            acceptIntent(LoginIntent.Login)
        }

        binding.logoutButton.setOnClickListener {
            acceptIntent(LoginIntent.Logout)
        }
    }

    override fun render(viewState: LoginViewState) {
        renderText(viewState)
        renderButtonsVisibility(viewState)
    }

    private fun renderText(viewState: LoginViewState) {
        binding.mainText.text = rootView.context.getString(R.string.main_welcome_text, viewState.name)
    }

    private fun renderButtonsVisibility(viewState: LoginViewState) {
        if (viewState.isLoggedIn) {
            binding.loginButton.visibility = View.GONE
            binding.logoutButton.visibility = View.VISIBLE
        } else {
            binding.loginButton.visibility = View.VISIBLE
            binding.logoutButton.visibility = View.GONE
        }
    }
}
