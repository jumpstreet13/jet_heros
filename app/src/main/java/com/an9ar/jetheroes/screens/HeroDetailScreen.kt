package com.an9ar.jetheroes.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingView
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.getImageUrl
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoDto
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HeroDetailScreen(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel,
    heroId: Long
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Back to others",
                        style = AppTheme.typography.textMediumBold,
                        color = AppTheme.colors.text
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = AppTheme.colors.iconColor,
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = AppTheme.colors.toolbar
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            val coroutineScope = rememberCoroutineScope()
            val swipeRefreshState = rememberSwipeRefreshState(false)
            val heroInfo =
                remember { mutableStateOf<GreatResult<HeroInfoDto>>(GreatResult.Progress) }

            LaunchedEffect(Unit) {
                val info = heroesViewModel.fetchHeroInfo(heroId)
                heroInfo.value = info
            }

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        swipeRefreshState.isRefreshing = true
                        heroInfo.value = heroesViewModel.fetchHeroInfo(heroId = heroId)
                        swipeRefreshState.isRefreshing = false
                    }
                }
            ) {
                when (val heroInfoResult = heroInfo.value) {
                    is GreatResult.Progress -> LoadingView(modifier = Modifier.fillMaxSize())
                    is GreatResult.Success -> HeroInfoContent(
                        heroInfoDto = heroInfoResult.data,
                        navHostController = navHostController
                    )
                    is GreatResult.Error -> ErrorItem(
                        message = heroInfoResult.exception.message.toString(),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        coroutineScope.launch {
                            heroInfo.value = GreatResult.Progress
                            heroInfo.value = heroesViewModel.fetchHeroInfo(heroId = heroId)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun HeroInfoContent(
    heroInfoDto: HeroInfoDto,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column(
        modifier = modifier
            .background(AppTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        BigHeroImage(url = heroInfoDto.thumbnail.getImageUrl())
        Spacer(modifier = Modifier.height(16.dp))
        BigHeroInfo(heroInfo = heroInfoDto)
        ComicsItem(navHostController, heroInfoDto)
    }
}

@Composable
fun ComicsItem(
    navHostController: NavHostController,
    heroInfoDto: HeroInfoDto
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navHostController.navigate(
                    "comicsInfo/${
                        heroInfoDto.comicsDto.collectionUri.toUri().path
                            ?.split(
                                "/"
                            )
                            ?.get(4)
                    }"
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberGlidePainter(
                request = "https://static.wikia.nocookie.net/character-power/images/8/8f/DC_Comics.png/revision/latest/scale-to-width-down/700?cb=20190203204448&path-prefix=ru",
                fadeIn = true,
            ),
            contentDescription = "Comics",
            modifier = Modifier
                .size(
                    height = 64.dp,
                    width = 84.dp
                )
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Watch all comics",
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
    }
}

@Composable
fun BigHeroImage(url: String) {
    Card(
        shape = RoundedCornerShape(32.dp),
        backgroundColor = AppTheme.colors.card,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(16.dp)
    ) {
        Image(
            painter = rememberGlidePainter(
                request = url,
                fadeIn = true,
                requestBuilder = { placeholder(R.drawable.default_image) }
            ),
            contentDescription = stringResource(R.string.hero_image_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BigHeroInfo(heroInfo: HeroInfoDto) {
    Column(
        modifier = Modifier
            .background(AppTheme.colors.background)
            .padding(16.dp)
    ) {
        Text(
            text = heroInfo.name,
            color = AppTheme.colors.text,
            style = AppTheme.typography.h3
        )
        Text(
            text = heroInfo.description,
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
    }
}
