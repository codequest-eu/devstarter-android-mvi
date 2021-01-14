package com.example.user.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.base.presentation.BaseFragment
import com.example.user.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewState, MainViewEvent, MainPresenter>(R.layout.fragment_main) {

    override val presenter by viewModels<MainPresenter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_button.setOnClickListener { presenter.acceptIntent(MainIntent.Login) }
        logout_button.setOnClickListener { presenter.acceptIntent(MainIntent.Logout) }
    }

    //region Render methods
    override fun render(viewState: MainViewState) {
        renderText(viewState)
        renderButtonsVisibility(viewState)
    }

    private fun renderText(viewState: MainViewState) {
        main_text.text = getString(R.string.main_welcome_text, viewState.name)
    }

    private fun renderButtonsVisibility(viewState: MainViewState) {
        if (viewState.isLoggedIn) {
            login_button.visibility = View.GONE
            logout_button.visibility = View.VISIBLE
        } else {
            login_button.visibility = View.VISIBLE
            logout_button.visibility = View.GONE
        }
    }

    override fun handle(viewEvent: MainViewEvent) {
        when (viewEvent) {
            is MainViewEvent.LoginFailed -> Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
            is MainViewEvent.Navigate -> navigation.navigate(viewEvent.destination)
        }
    }
}