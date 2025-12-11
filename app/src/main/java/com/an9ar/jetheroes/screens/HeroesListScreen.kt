package com.an9ar.jetheroes.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.ThemeToggle
import com.an9ar.jetheroes.brandbook.BrightnessMedium
import com.an9ar.jetheroes.brandbook.DataViewItem
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingItem
import com.an9ar.jetheroes.common.LoadingView
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoDto
import com.an9ar.jetheroes.data.dto.heroinfo.toDataViewModel
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HeroesListScreen(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel = hiltViewModel(),
    onToggleTheme: ThemeToggle,
) {
    HeroesListContent(
        navHostController = navHostController,
        heroesViewModel = heroesViewModel,
        onToggleTheme
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HeroesListContent(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel,
    onToggleTheme: ThemeToggle,
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
                },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            modifier = Modifier.size(20.dp, 32.dp),
                            imageVector = Icons.Rounded.BrightnessMedium,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) {
        Surface(color = AppTheme.colors.background) {
            val lazyMovieItems: LazyPagingItems<HeroInfoDto> =
                heroesViewModel.heroes.collectAsLazyPagingItems()
            val swipeRefreshState = rememberSwipeRefreshState(false)
            val context = LocalContext.current

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { lazyMovieItems.refresh() }
            ) {
                LazyColumn {
                    items(lazyMovieItems) { hero ->
                        hero?.let {
                            DataViewItem(
                                dataViewModel = it.toDataViewModel("hero/${it.id.toLongOrNull() ?: 0L}"),
                                navHostController = navHostController
                            )
                        }
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
                                            onClickRetry = { lazyMovieItems.retry() }
                                        )
                                    }
                                }
                            }
                            loadState.append is LoadState.Error -> {
                                val e = lazyMovieItems.loadState.append as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        onClickRetry = { lazyMovieItems.retry() }
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
