package com.an9ar.jetheroes

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.an9ar.jetheroes.heroesscreen.ComicsViewModel
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.screens.ComicsScreen
import com.an9ar.jetheroes.screens.HeroDetailScreen
import com.an9ar.jetheroes.screens.HeroesListScreen
import com.an9ar.jetheroes.screens.SplashScreen
import com.an9ar.jetheroes.theme.AppTheme
import com.an9ar.jetheroes.theme.JetHeroesTheme

@Composable
fun JetHeroessApp(
        heroesViewModel: HeroesViewModel,
        comicsViewModel: ComicsViewModel
) {
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
                composable("comicsInfo") {
                    ComicsScreen(
                            navHostController = navController,
                            viewModel = comicsViewModel
                    )
                }
                composable(
                        "hero/{heroId}",
                        arguments = listOf(navArgument("heroId") { type = NavType.LongType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getLong("heroId")?.let { id ->
                        HeroDetailScreen(
                                navHostController = navController,
                                heroesViewModel = heroesViewModel,
                                heroId = id
                        )
                    }
                }
            }
        }
    }
}