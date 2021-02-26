package com.example.user.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.base.presentation.BaseFragment
import com.example.user.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class LoginFragment : BaseFragment<LoginViewState, LoginViewEvent, LoginPresenter>() {

    override val presenter by viewModels<LoginPresenter>()

    private var loginView: LoginMviView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LoginMviView(inflater, container, presenter::acceptIntent).let {
            loginView = it
            it.rootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.go_to_destination).setOnClickListener {
            navigation.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginView = null
    }

    override fun handleEvent(viewEvent: LoginViewEvent) {
        when (viewEvent) {
            is LoginViewEvent.LoginFailed -> Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
            is LoginViewEvent.LoginSuccess -> navigation.navigate(LoginFragmentDirections.actionLoginToHome("hi!"))
        }
    }

    override fun render(viewState: LoginViewState) {
        loginView?.render(viewState)
    }
}
