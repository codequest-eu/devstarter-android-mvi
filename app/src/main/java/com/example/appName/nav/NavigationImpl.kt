package com.example.appName.nav

import android.content.Context
import androidx.navigation.findNavController
import com.example.appName.MainActivity
import com.example.appName.R
import com.example.base.nav.Direction
import com.example.base.nav.Navigation
import com.example.home.HomeFragment
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class NavigationImpl @Inject constructor(
        @ActivityContext val context: Context
) : Navigation {

    private val navController by lazy {
        (context as MainActivity).findNavController(R.id.mainFrame)
    }

    override fun navigate(direction: Direction) {
        when (direction) {
            is Direction.toHomeScreen -> {
                navController.navigate(R.id.action_login_to_home, HomeFragment.bundle(context.getString(direction.textRes)))
            }
        }
    }
}
