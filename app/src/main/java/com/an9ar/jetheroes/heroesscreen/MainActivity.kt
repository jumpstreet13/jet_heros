package com.an9ar.jetheroes.heroesscreen

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.screens.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var heroRepository: HeroRepository

    // todo поменять на инжекцию в конструктор
    private val heroesViewModel: HeroesViewModel by viewModels {
        HeroesViewModelFactory(this, heroRepository, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavigationScreen(heroesViewModel)
            // todo замутить навигацию
            /* val navController = rememberNavController()
             val actions = remember(navController) { Actions(navController) }
             NavHost(
                 navController = navController,
                 startDestination = Destinations.MAIN
             ) {
                 composable(Destinations.SPLASH) { SplashScreen(actions, lifecycleScope) }
                 composable(Destinations.MAIN) { MainNavigationScreen(heroesViewModel) }
             }*/
        }
    }
}