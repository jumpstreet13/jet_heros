package com.an9ar.jetheroes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.screens.Actions
import com.an9ar.jetheroes.screens.Destinations
import com.an9ar.jetheroes.screens.MainNavigationScreen
import com.an9ar.jetheroes.screens.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var heroRepository: HeroRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Временно, чтобы чекать как работает сэрвис
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    val response = heroRepository.loadHeroes()

                    Log.e("HERO", response.toString())
                }catch (e: Throwable){

                }
            }
        }
        setContent {
            val navController = rememberNavController()
            val actions = remember(navController) { Actions(navController) }
            NavHost(
                navController = navController,
                startDestination = Destinations.SPLASH
            ) {
                composable(Destinations.SPLASH) { SplashScreen(actions, lifecycleScope) }
                composable(Destinations.MAIN) { MainNavigationScreen(actions, lifecycleScope) }
            }
        }
    }
}