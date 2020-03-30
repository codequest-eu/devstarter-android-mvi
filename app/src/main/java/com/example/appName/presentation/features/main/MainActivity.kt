package com.example.appName.presentation.features.main

import android.os.Bundle
import android.view.View
import com.example.appName.R
import com.example.appName.presentation.features.base.BaseActivity
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewState, MainPresenter>(
        R.layout.activity_main
), MainView {
    //region Intents
    override val loginIntent: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val logoutIntent: PublishSubject<Unit> = PublishSubject.create<Unit>()
    //endregion

    //region Render methods
    override fun render(viewState: MainViewState) {
        with(viewState) {
            renderText(mainText)
            renderButtonsVisibility(isLoggedIn)
        }
    }

    private fun renderText(text: String) {
        main_text.text = text
    }

    private fun renderButtonsVisibility(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            login_button.visibility = View.GONE
            logout_button.visibility = View.VISIBLE
        } else {
            login_button.visibility = View.VISIBLE
            logout_button.visibility = View.GONE
        }
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindIntents()
    }

    private fun bindIntents() {
        login_button.setOnClickListener {
            loginIntent.onNext(Unit)
        }

        logout_button.setOnClickListener {
            logoutIntent.onNext(Unit)
        }
    }
}
