package com.an9ar.jetheroes

import android.content.res.AssetManager
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.screens.HeroesListScreen
import com.an9ar.jetheroes.screens.SplashScreen
import com.an9ar.jetheroes.theme.AppTheme
import com.an9ar.jetheroes.theme.JetHeroesTheme

@Composable
fun JetHeroessApp(assets: AssetManager, heroesViewModel: HeroesViewModel) {
    JetHeroesTheme {
        Surface(color = AppTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(navHostController = navController)
                }
                composable("heroesList") {
                    HeroesListScreen(
                        navHostController = navController,
                        heroesViewModel = heroesViewModel
                    )
                }
                composable(
                    "hero/{heroId}"
                ) {

                }
            }
        }
    }
}