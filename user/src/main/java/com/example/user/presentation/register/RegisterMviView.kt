package com.example.user.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.base.presentation.BaseMviView
import com.example.user.R

class RegisterMviView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        override val acceptIntent: (RegisterIntent) -> Unit
) : BaseMviView<RegisterViewState, RegisterIntent>() {
    override val rootView = layoutInflater.inflate(R.layout.fragment_register, parent, false)

    val usernameEdit = rootView.findViewById<EditText>(R.id.register_username_edit)
    val passwordEdit = rootView.findViewById<EditText>(R.id.register_password_edit)
    val registerBtn = rootView.findViewById<Button>(R.id.register_button)

    init {
        registerBtn.setOnClickListener {
            acceptIntent(RegisterIntent.Register(
                    username = usernameEdit.text.toString(),
                    password = passwordEdit.text.toString()
            ))
        }
    }

    override fun render(viewState: RegisterViewState) {
    }
}
