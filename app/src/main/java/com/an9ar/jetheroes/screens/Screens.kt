package com.an9ar.jetheroes.screens

sealed class Screens(val routeName: String) {
    object SplashScreen : Screens(routeName = "SplashScreen")
}