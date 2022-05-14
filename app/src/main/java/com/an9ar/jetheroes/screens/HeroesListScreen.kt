package com.an9ar.jetheroes.screens

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.an9ar.jetheroes.brandbook.DataViewItem
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingItem
import com.an9ar.jetheroes.common.LoadingView
import com.an9ar.jetheroes.data.dto.heroinfo.HeroResponse
import com.an9ar.jetheroes.data.dto.heroinfo.toDataView
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
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
                        DataViewItem(
                            dataViewModel = hero!!.toDataView("hero/${hero.id}"),
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
