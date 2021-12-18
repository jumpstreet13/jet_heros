package com.an9ar.jetheroes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.an9ar.jetheroes.heroesscreen.ComicsViewModel
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val heroesViewModel: HeroesViewModel by viewModels()
    private val comicsViewModel: ComicsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            JetHeroessApp(
                    heroesViewModel = heroesViewModel,
                    comicsViewModel = comicsViewModel
            )
        }
    }
}