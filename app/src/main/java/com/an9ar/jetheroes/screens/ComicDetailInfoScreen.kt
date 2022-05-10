package com.an9ar.jetheroes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsDto
import com.an9ar.jetheroes.data.dto.getImageUrl
import com.an9ar.jetheroes.heroesscreen.ComicsViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun ComicDetailInfoScreen(
    navHostController: NavHostController,
    viewModel: ComicsViewModel,
    comicsId: String
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
            val comicsInfo =
                remember { mutableStateOf<GreatResult<ComicsDto>>(GreatResult.Progress) }

            LaunchedEffect(Unit) {
                val info = viewModel.fetchComicDetailInfo(comicsId)
                comicsInfo.value = info
            }

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        swipeRefreshState.isRefreshing = true
                        comicsInfo.value = viewModel.fetchComicDetailInfo(comicsId)
                        swipeRefreshState.isRefreshing = false
                    }
                }
            ) {
                when (val comicsInfoDto = comicsInfo.value) {
                    is GreatResult.Progress -> HeroInfoLoading()
                    is GreatResult.Success -> ComicsInfoContent(
                        comicsDto = comicsInfoDto.data,
                        navHostController = navHostController
                    )
                    is GreatResult.Error -> HeroInfoError(modifier)
                }
            }
        }
    )
}

@Composable
fun ComicsInfoContent(
    comicsDto: ComicsDto,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Column(
        modifier = modifier
            .background(AppTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        BigHeroImage(url = comicsDto.thumbnail.getImageUrl())
        Spacer(modifier = Modifier.height(16.dp))
        BigComicsInfo(comicsInfo = comicsDto)
    }
}

@Composable
fun BigComicsInfo(comicsInfo: ComicsDto) {
    Column(
        modifier = Modifier
            .background(AppTheme.colors.background)
            .padding(16.dp)
    ) {
        Text(
            text = comicsInfo.title,
            color = AppTheme.colors.text,
            style = AppTheme.typography.h3
        )
        if (comicsInfo.description != null) {
            Text(
                text = comicsInfo.description,
                color = AppTheme.colors.text,
                style = AppTheme.typography.textMediumBold
            )
        }
    }
}