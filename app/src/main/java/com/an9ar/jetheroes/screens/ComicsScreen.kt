package com.an9ar.jetheroes.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsDto
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.data.dto.getImageUrl
import com.an9ar.jetheroes.heroesscreen.ComicsViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun ComicsScreen(
    navHostController: NavHostController,
    viewModel: ComicsViewModel
) {
    ComicsListContent(
        navHostController = navHostController,
        viewModel = viewModel
    )
}

@Composable
fun ComicsListContent(
    navHostController: NavHostController,
    viewModel: ComicsViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = AppTheme.colors.toolbar,
                title = {
                    Text(
                        text = "Comics",
                        style = AppTheme.typography.textMediumBold,
                        color = AppTheme.colors.text
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(color = AppTheme.colors.background) {

            val modifier = Modifier.padding(innerPadding)
            val coroutineScope = rememberCoroutineScope()
            val swipeRefreshState = rememberSwipeRefreshState(false)
            val comicsInfo =
                remember { mutableStateOf<GreatResult<ComicsWrapperDto>>(GreatResult.Progress) }

            LaunchedEffect(Unit) {
                val info = viewModel.fetchComicsInfo("http://gateway.marvel.com/v1/public/characters/1017100/comics")
                comicsInfo.value = info
            }

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        swipeRefreshState.isRefreshing = true
                        comicsInfo.value =
                            viewModel.fetchComicsInfo("http://gateway.marvel.com/v1/public/characters/1017100/comics")
                        swipeRefreshState.isRefreshing = false
                    }
                }
            ) {
                when (val comicsInfoRequest = comicsInfo.value) {
                    is GreatResult.Progress -> {}/*HeroInfoLoading()*/
                    is GreatResult.Success -> Comics(
                        comicsWrapper = comicsInfoRequest.data,
                        navHostController = navHostController
                    )
                    is GreatResult.Error -> {}/*HeroInfoError(modifier)*/
                }
            }
        }
    }
}

@Composable
fun Comics(
    comicsWrapper: ComicsWrapperDto,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    LazyColumn() {
        items(comicsWrapper.results) { comics ->
            ComicsItem(
                comics = comics,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun ComicsItem(
    comics: ComicsDto,
    navHostController: NavHostController
) {
    Card(
        backgroundColor = AppTheme.colors.card,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(onClick = { })
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
            ComicsTitle(
                title = comics.title,
                modifier = Modifier.weight(1f)
            )
            ComicsImage(
                imageUrl = comics.thumbnail.getImageUrl()
            )
        }
    }
}

@Composable
fun ComicsImage(imageUrl: String) {

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
fun ComicsTitle(
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
