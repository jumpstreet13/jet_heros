package com.an9ar.jetheroes.screens

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.an9ar.jetheroes.screens.Destinations.MAIN

object Destinations {
    const val SPLASH = "splash_screen"
    const val MAIN = "main_nav_screen"
}

class Actions(navController: NavController) {
    val openMainScreen: () -> Unit = {
        navController.navigate(MAIN)
    }
}