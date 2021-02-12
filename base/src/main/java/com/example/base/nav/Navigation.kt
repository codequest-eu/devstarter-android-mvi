package com.example.base.nav

import androidx.navigation.NavDirections

interface Navigation {

    fun navigate(direction: NavDirections)

    fun navigateBack()
}
