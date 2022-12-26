package com.an9ar.jetheroes.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.brandbook.DataViewItem
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingView
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.data.dto.comicsinfo.toDataViewModel
import com.an9ar.jetheroes.heroesscreen.ComicsViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun ComicsScreen(
    navHostController: NavHostController,
    viewModel: ComicsViewModel,
    comicsId: String
) {
    ComicsListContent(
        navHostController = navHostController,
        viewModel = viewModel,
        comicsId = comicsId
    )
}

@Composable
fun ComicsListContent(
    navHostController: NavHostController,
    viewModel: ComicsViewModel,
    comicsId: String
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
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = AppTheme.colors.iconColor,
                            contentDescription = null
                        )
                    }
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

            LaunchedEffect(true) {
                val info = viewModel.fetchComicsInfoById(comicsId)
                comicsInfo.value = info
            }

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        swipeRefreshState.isRefreshing = true
                        comicsInfo.value = viewModel.fetchComicsInfoById(comicsId)
                        swipeRefreshState.isRefreshing = false
                    }
                }
            ) {
                when (val comicsInfoRequest = comicsInfo.value) {
                    is GreatResult.Progress -> {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }
                    is GreatResult.Success -> Comics(
                        comicsWrapper = comicsInfoRequest.data,
                        navHostController = navHostController
                    )
                    is GreatResult.Error -> {
                        ErrorItem(
                            message = comicsInfoRequest.exception.message.toString(),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            coroutineScope.launch {
                                comicsInfo.value = GreatResult.Progress
                                comicsInfo.value = viewModel.fetchComicsInfoById(comicsId)
                            }
                        }
                    }
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
    LazyColumn(modifier = modifier) {
        items(comicsWrapper.results) { comics ->
            DataViewItem(
                navHostController = navHostController,
                dataViewModel = comics.toDataViewModel("comicInfo/${comics.id}")
            )
        }
    }
}
