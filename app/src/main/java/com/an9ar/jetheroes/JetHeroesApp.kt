package com.an9ar.jetheroes

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.an9ar.jetheroes.screens.ComicDetailInfoScreen
import com.an9ar.jetheroes.screens.ComicsScreen
import com.an9ar.jetheroes.screens.HeroDetailScreen
import com.an9ar.jetheroes.screens.HeroesListScreen
import com.an9ar.jetheroes.screens.SplashScreen
import com.an9ar.jetheroes.theme.AppTheme
import com.an9ar.jetheroes.theme.JetHeroesTheme

typealias ThemeToggle = () -> Unit

@Composable
fun JetHeroessApp(onToggleTheme: () -> Unit, darkTheme: Boolean) {

    JetHeroesTheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(navHostController = navController)
                }
                composable("heroesList") {
                    HeroesListScreen(navHostController = navController, onToggleTheme = onToggleTheme)
                }
                composable(
                    "comicsInfo/{comicsId}",
                    arguments = listOf(navArgument("comicsId") { type = NavType.StringType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getString("comicsId")?.let {
                        ComicsScreen(
                            navHostController = navController,
                            comicsId = it
                        )
                    }
                }
                composable(
                    "hero/{heroId}",
                    arguments = listOf(navArgument("heroId") { type = NavType.LongType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getLong("heroId")?.let { id ->
                        HeroDetailScreen(
                            navHostController = navController,
                            heroId = id
                        )
                    }
                }
                composable(
                    "comicInfo/{comicInfoId}",
                    arguments = listOf(navArgument("comicInfoId") { type = NavType.StringType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getString("comicInfoId")?.let { id ->
                        ComicDetailInfoScreen(
                            navHostController = navController,
                            comicsId = id
                        )
                    }
                }
            }
        }
    }
}