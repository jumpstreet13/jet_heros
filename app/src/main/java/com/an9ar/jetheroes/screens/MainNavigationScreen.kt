package com.an9ar.jetheroes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.JetHeroesTheme
import com.google.accompanist.glide.rememberGlidePainter
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun MainNavigationScreen(
    heroesViewModel: HeroesViewModel
) {
    JetHeroesTheme {
        Surface(color = MaterialTheme.colors.background) {
            // todo список тормозит, разобраться в чем дело
            MovieList(movies = heroesViewModel.movies)
            // todo заимплементить навигацию
          /*  val navHostController = rememberNavController()
            NavHost(
                navController = navHostController,
                startDestination = Screens.SplashScreen.routeName
            ) {
            }*/
        }
    }
}

// todo вынести функции в отдельный файл
@Composable
fun MovieList(movies: Flow<PagingData<HeroResponse>>) {
    val lazyMovieItems: LazyPagingItems<HeroResponse> = movies.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyMovieItems) { hero ->
            HeroItem(hero = hero!!)
        }
    }
}

@Composable
fun HeroItem(hero: HeroResponse) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeroTitle(
            hero.name,
            modifier = Modifier.weight(1f)
        )
        HeroImage(
            hero.thumbnail.path,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(90.dp)
        )
    }
}

@Composable
fun HeroImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    // todo выбрать что подходит лучше коил или глайд


    // todo  разобраться почему не грузятся картинки
     Image(
         painter = rememberGlidePainter(imageUrl),
         contentDescription = stringResource(R.string.hero_image_description),
         contentScale = ContentScale.Crop,
         modifier = modifier
     )

    // todo добавить картинки для лоадинга и ошибки
    /*CoilImage(
        data = imageUrl,
        modifier = modifier,
        fadeIn = true,
        contentScale = ContentScale.Crop,
        loading = {
            Image(
                imageVector = ImageVector.Companion.vectorResource(R.drawable.marvel_logo),
                alpha = 0.45f,
                contentDescription = stringResource(id = R.string.hero_image_description)
            )
        },
        error = {
            Image(
                imageVector = ImageVector.Companion.vectorResource(id = R.drawable.ic_launcher_background),
                alpha = 0.45f,
                contentDescription = stringResource(id = R.string.hero_image_description)
            )
        }
    )*/
}

@Composable
fun HeroTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title,
        maxLines = 2,
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis
    )
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