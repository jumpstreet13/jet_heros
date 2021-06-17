package com.an9ar.jetheroes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun HeroesListScreen(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel
) {
    HeroesListContent(
        navHostController = navHostController,
        heroesViewModel = heroesViewModel
    )
}

@Composable
fun HeroesListContent(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = AppTheme.colors.toolbar,
                title = {
                    Text(
                        text = "Marvel heroes",
                        style = AppTheme.typography.h3,
                        color = AppTheme.colors.text
                    )
                }
            )
        }
    ) {
        Surface(color = AppTheme.colors.background) {
            val lazyMovieItems: LazyPagingItems<HeroResponse> =
                heroesViewModel.heroes.collectAsLazyPagingItems()

            LazyColumn {
                items(lazyMovieItems) { hero ->
                    HeroItem(
                        hero = hero!!,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun HeroItem(
    hero: HeroResponse,
    navHostController: NavHostController
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = {
                navHostController.navigate("// todo ")
            })
            .fillMaxWidth()
    ) {
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
            HeroImage("${hero.thumbnail.path}.${hero.thumbnail.extension}")
        }
    }
}

@Composable
fun HeroImage(imageUrl: String) {

    Image(
        painter = rememberGlidePainter(
            request = imageUrl,
            fadeIn = true
        ),

        contentDescription = stringResource(R.string.hero_image_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(120.dp)
            .width(120.dp)
            .padding(start = 16.dp)
            .clip(RoundedCornerShape(16.dp))
    )
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