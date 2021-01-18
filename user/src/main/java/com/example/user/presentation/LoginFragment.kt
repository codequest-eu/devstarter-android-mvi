package com.example.user.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.base.presentation.BaseFragment
import com.example.base.utils.ViewBindingProperty
import com.example.user.R
import com.example.user.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewState, LoginViewEvent, LoginPresenter>(R.layout.fragment_login) {
    override val presenter by viewModels<LoginPresenter>()

    private val viewBinding by ViewBindingProperty(this, FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.loginButton.setOnClickListener { presenter.acceptIntent(LoginIntent.Login) }
        viewBinding.logoutButton.setOnClickListener { presenter.acceptIntent(LoginIntent.Logout) }
    }

    //region Render methods
    override fun render(viewState: LoginViewState) {
        renderText(viewState)
        renderButtonsVisibility(viewState)
    }

    private fun renderText(viewState: LoginViewState) {
        viewBinding.mainText.text = getString(R.string.main_welcome_text, viewState.name)
    }

    private fun renderButtonsVisibility(viewState: LoginViewState) {
        if (viewState.isLoggedIn) {
            viewBinding.loginButton.visibility = View.GONE
            viewBinding.logoutButton.visibility = View.VISIBLE
        } else {
            viewBinding.loginButton.visibility = View.VISIBLE
            viewBinding.logoutButton.visibility = View.GONE
        }
    }

    override fun handle(viewEvent: LoginViewEvent) {
        when (viewEvent) {
            is LoginViewEvent.LoginFailed -> Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
            is LoginViewEvent.Navigate -> navigation.navigate(viewEvent.destination)
        }
    }
}
