package com.an9ar.jetheroes.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingItem
import com.an9ar.jetheroes.common.LoadingView
import com.an9ar.jetheroes.data.dto.getImageUrl
import com.an9ar.jetheroes.data.dto.heroinfo.HeroResponse
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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
                        style = AppTheme.typography.textMediumBold,
                        color = AppTheme.colors.text
                    )
                }
            )
        }
    ) {
        Surface(color = AppTheme.colors.background) {
            val lazyMovieItems: LazyPagingItems<HeroResponse> =
                heroesViewModel.heroes.collectAsLazyPagingItems()
            val swipeRefreshState = rememberSwipeRefreshState(false)
            val context = LocalContext.current

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { lazyMovieItems.refresh() }
            ) {
                LazyColumn {
                    items(lazyMovieItems) { hero ->
                        HeroItem(
                            hero = hero!!,
                            navHostController = navHostController
                        )
                    }

                    lazyMovieItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                if (swipeRefreshState.isSwipeInProgress) {
                                    swipeRefreshState.isRefreshing = false
                                } else {
                                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                                }
                            }
                            loadState.append is LoadState.Loading -> {
                                item { LoadingItem() }
                            }
                            loadState.refresh is LoadState.Error -> {
                                val e = lazyMovieItems.loadState.refresh as LoadState.Error
                                if (lazyMovieItems.itemCount > 0) {
                                    Toast.makeText(
                                        context,
                                        e.error.localizedMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                item {
                                    if (lazyMovieItems.itemCount == 0) {
                                        ErrorItem(
                                            message = e.error.localizedMessage!!,
                                            modifier = Modifier.fillParentMaxSize(),
                                            onClickRetry = { retry() }
                                        )
                                    }
                                }
                            }
                            loadState.append is LoadState.Error -> {
                                val e = lazyMovieItems.loadState.append as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
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
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(onClick = { navHostController.navigate("hero/${hero.id}") })
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeroTitle(
                title = hero.name,
                modifier = Modifier.weight(1f)
            )
            HeroImage(
                imageUrl = hero.thumbnail.getImageUrl()
            )
        }
    }
}

@Composable
fun HeroImage(imageUrl: String) {
    Image(
        painter = rememberGlidePainter(
            request = imageUrl,
            fadeIn = true,
            // todo плохая идея
            requestBuilder = { placeholder(R.drawable.default_image) }
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
        style = AppTheme.typography.textMediumBold,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.text
    )
}
