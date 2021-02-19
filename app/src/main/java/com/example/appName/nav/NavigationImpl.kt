package com.example.appName.nav

import android.content.Context
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.appName.MainActivity
import com.example.appName.R
import com.example.base.nav.Navigation
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class NavigationImpl @Inject constructor(
    @ActivityContext val context: Context
) : Navigation {

    private val navController by lazy {
        (context as MainActivity).findNavController(R.id.main_frame)
    }

    override fun navigate(direction: NavDirections) {
        navController.navigate(direction)
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}
