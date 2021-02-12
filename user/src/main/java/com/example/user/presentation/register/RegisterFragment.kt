package com.example.user.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.base.nav.Navigation
import com.example.user.R
import javax.inject.Inject

class RegisterFragment : Fragment(R.layout.fragment_register) {

    @Inject
    lateinit var navigation: Navigation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.destination_go_back_button)
                .setOnClickListener { navigation.navigateBack() }
    }
}
