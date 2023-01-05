package com.an9ar.jetheroes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingView
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
    viewModel: ComicsViewModel = hiltViewModel(),
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
                    is GreatResult.Progress -> LoadingView(modifier = Modifier.fillMaxSize())
                    is GreatResult.Success -> ComicsInfoContent(
                        comicsDto = comicsInfoDto.data,
                        navHostController = navHostController
                    )
                    is GreatResult.Error -> ErrorItem(
                        message = comicsInfoDto.exception.message.toString(),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        coroutineScope.launch {
                            comicsInfo.value = GreatResult.Progress
                            comicsInfo.value = viewModel.fetchComicDetailInfo(comicsId)
                        }
                    }
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