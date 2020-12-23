package com.an9ar.jetheroes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.screens.MainNavigationScreen
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
                val response = heroRepository.loadHeroes()

                Log.e("HERO", response.toString())
            }
        }
        setContent {
            MainNavigationScreen()
        }
    }
}