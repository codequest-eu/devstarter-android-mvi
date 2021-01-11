package com.example.appName.presentation.features.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.appName.R
import com.example.appName.presentation.features.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewState, MainPresenter>(
        R.layout.activity_main
) {
    override val presenter by viewModels<MainPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.acceptIntent(MainIntent.Dummy)

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
    //endregion
}
