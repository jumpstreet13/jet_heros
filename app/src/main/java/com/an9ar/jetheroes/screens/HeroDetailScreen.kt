package com.an9ar.jetheroes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.HeroInfoDto
import com.an9ar.jetheroes.data.dto.getImageUrl
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun HeroDetailScreen(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel,
    heroId: Long
) {
    // we can provide it like that val heroesViewModel = viewModel<HeroesViewModel>()
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
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = AppTheme.colors.toolbar
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            val heroInfo =
                remember { mutableStateOf<GreatResult<HeroInfoDto>>(GreatResult.Progress) }

            LaunchedEffect(Unit) {
                val info = heroesViewModel.fetchHeroInfo(heroId)
                heroInfo.value = info
            }

            when (val heroInfoResult = heroInfo.value) {
                is GreatResult.Progress -> HeroInfoLoading(modifier)
                is GreatResult.Success -> HeroInfoContent(
                    heroInfoDto = heroInfoResult.data,
                    modifier = modifier
                )
                is GreatResult.Error -> HeroInfoError(modifier)
            }
        }
    )
}

@Composable
fun HeroInfoContent(
    heroInfoDto: HeroInfoDto,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(AppTheme.colors.background)
            .fillMaxSize()
    ) {
        item {
            BigHeroImage(url = heroInfoDto.thumbnail.getImageUrl())
            Spacer(modifier = Modifier.height(16.dp))
            BigHeroInfo(heroInfo = heroInfoDto)
        }
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
            style = AppTheme.typography.h3
        )
        Text(
            text = heroInfo.description,
            style = AppTheme.typography.textMediumBold
        )
    }
}

@Composable
fun HeroInfoLoading(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.primary,
            strokeWidth = 4.dp,
        )
    }
}

@Composable
fun HeroInfoError(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "You are fucked up",
            color = AppTheme.colors.error
        )
    }
}
