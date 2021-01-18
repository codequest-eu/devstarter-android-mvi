package com.example.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.base.presentation.BaseFragment
import com.example.user.R
import com.example.user.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewState, LoginViewEvent, LoginPresenter>() {

    override val presenter by viewModels<LoginPresenter>()

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentLoginBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginButton?.setOnClickListener { presenter.acceptIntent(LoginIntent.Login) }
        binding?.logoutButton?.setOnClickListener { presenter.acceptIntent(LoginIntent.Logout) }
    }

    //region Render methods
    override fun render(viewState: LoginViewState) {
        renderText(viewState)
        renderButtonsVisibility(viewState)
    }

    private fun renderText(viewState: LoginViewState) {
        binding?.mainText?.text = getString(R.string.main_welcome_text, viewState.name)
    }

    private fun renderButtonsVisibility(viewState: LoginViewState) {
        if (viewState.isLoggedIn) {
            binding?.loginButton?.visibility = View.GONE
            binding?.logoutButton?.visibility = View.VISIBLE
        } else {
            binding?.loginButton?.visibility = View.VISIBLE
            binding?.logoutButton?.visibility = View.GONE
        }
    }

    override fun handle(viewEvent: LoginViewEvent) {
        when (viewEvent) {
            is LoginViewEvent.LoginFailed -> Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
            is LoginViewEvent.Navigate -> navigation.navigate(viewEvent.destination)
        }
    }
}
