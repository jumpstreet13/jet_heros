package com.an9ar.jetheroes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LifecycleCoroutineScope
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.theme.JetHeroesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainNavigationScreen(navActions: Actions, lifeCycleScope: LifecycleCoroutineScope) {
    JetHeroesTheme {
        Surface(color = MaterialTheme.colors.background) {

        }
    }
}

@Composable
fun SplashScreen(navActions: Actions, lifeCycleScope: LifecycleCoroutineScope) {
    JetHeroesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val image: Painter = painterResource(id = R.drawable.marvel_logo)
            Image(
                painter = image,
                modifier = Modifier
                    .fillMaxSize(),
                 contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            lifeCycleScope.launch {
                delay(1000)
                navActions.openMainScreen.invoke()
            }
        }
    }
}