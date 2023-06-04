package com.an9ar.jetheroes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import design.andromedacompose.components.reveal.CircularReveal

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLightTheme by remember { mutableStateOf(true) }
            CircularReveal(
                targetState = isLightTheme,
                animationSpec = tween(2500)
            ) {
                JetHeroessApp(
                    darkTheme = !isLightTheme,
                    onToggleTheme = {
                        isLightTheme = !isLightTheme
                    },
                )
            }
        }
    }
}