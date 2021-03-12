package com.example.user.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.base.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewState, RegisterViewEvent, RegisterPresenter>() {

    override val presenter by viewModels<RegisterPresenter>()

    private lateinit var registerView: RegisterMviView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerView = RegisterMviView(inflater, container, ::acceptIntent)
        return registerView.rootView
    }

    fun acceptIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.GoBack -> navigation.navigateBack()
            else -> presenter.acceptIntent(intent)
        }
    }

    override fun handleEvent(viewEvent: RegisterViewEvent) {
    }

    override fun render(viewState: RegisterViewState) {
        registerView.render(viewState)
    }
}
