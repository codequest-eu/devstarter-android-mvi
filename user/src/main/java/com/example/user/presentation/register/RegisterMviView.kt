package com.example.user.presentation.register

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.base.presentation.BaseMviView
import com.example.user.databinding.FragmentRegisterBinding

class RegisterMviView(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    override val acceptIntent: (RegisterIntent) -> Unit
) : BaseMviView<RegisterViewState, RegisterIntent>() {
    private val binding = FragmentRegisterBinding.inflate(layoutInflater, parent, false)
    override val rootView = binding.root

    init {
        binding.registerButton.setOnClickListener {
            acceptIntent(
                RegisterIntent.Register(
                    username = binding.registerUsernameEdit.text.toString(),
                    password = binding.registerPasswordEdit.text.toString()
                )
            )
        }

        binding.destinationGoBackButton.setOnClickListener {
            acceptIntent(RegisterIntent.GoBack)
        }
    }

    override fun render(viewState: RegisterViewState) {
    }
}
