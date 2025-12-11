package com.an9ar.jetheroes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.an9ar.jetheroes.R
import com.an9ar.jetheroes.common.ErrorItem
import com.an9ar.jetheroes.common.LoadingView
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.heroinfo.HeroInfoDto
import com.an9ar.jetheroes.heroesscreen.HeroesViewModel
import com.an9ar.jetheroes.theme.AppTheme
import com.an9ar.jetheroes.utils.FallbackImageHelper
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun HeroDetailScreen(
    navHostController: NavHostController,
    heroesViewModel: HeroesViewModel = hiltViewModel(),
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

            val heroInfo: State<GreatResult<HeroInfoDto>> = getState(heroesViewModel, heroId)

            SwipeRefresh(
                modifier = modifier,
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        swipeRefreshState.isRefreshing = true
                        heroInfo.unsafeMutable().value = heroesViewModel.fetchHeroInfo(heroId = heroId)
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
                            heroInfo.unsafeMutable().value = GreatResult.Progress
                            heroInfo.unsafeMutable().value = heroesViewModel.fetchHeroInfo(heroId = heroId)
                        }
                    }
                }
            }
        }
    )
}

inline fun <reified T> State<T>.unsafeMutable(): MutableState<T> {
    return this as MutableState<T>
}

@Composable
private fun getState(heroesViewModel: HeroesViewModel, heroId: Long): State<GreatResult<HeroInfoDto>> {
    return produceState<GreatResult<HeroInfoDto>>(initialValue = GreatResult.Progress) {
        value = heroesViewModel.fetchHeroInfo(heroId)
    }
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
        BigHeroImage(url = heroInfoDto.image.url, heroName = heroInfoDto.name)
        Spacer(modifier = Modifier.height(16.dp))
        BigHeroInfo(heroInfo = heroInfoDto)
        PowerStatsSection(heroInfoDto.powerstats)
        BiographySection(heroInfoDto.biography)
        AppearanceSection(heroInfoDto.appearance)
        WorkSection(heroInfoDto.work)
        ConnectionsSection(heroInfoDto.connections)
    }
}

@Composable
fun BigHeroImage(url: String, heroName: String = "") {
    // If URL is from superherodb.com (known to have 403 issues), use fallback immediately
    val shouldUseFallback = remember(url) { 
        url.contains("superherodb.com", ignoreCase = true)
    }
    val fallbackUrl = remember(heroName) { 
        FallbackImageHelper.getFallbackImageForHero(heroName.ifEmpty { "default" })
    }
    val finalImageUrl = if (shouldUseFallback) fallbackUrl else url
    
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
                request = finalImageUrl,
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = heroInfo.getDescription(),
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
    }
}

@Composable
fun PowerStatsSection(powerstats: com.an9ar.jetheroes.data.dto.heroinfo.PowerStats?) {
    powerstats?.let { stats ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            backgroundColor = AppTheme.colors.card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Power Stats",
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.textMediumBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                stats.intelligence?.let { StatRow("Intelligence", it) }
                stats.strength?.let { StatRow("Strength", it) }
                stats.speed?.let { StatRow("Speed", it) }
                stats.durability?.let { StatRow("Durability", it) }
                stats.power?.let { StatRow("Power", it) }
                stats.combat?.let { StatRow("Combat", it) }
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
        Text(
            text = value,
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
    }
}

@Composable
fun BiographySection(biography: com.an9ar.jetheroes.data.dto.heroinfo.Biography?) {
    biography?.let { bio ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            backgroundColor = AppTheme.colors.card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Biography",
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.textMediumBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                bio.fullName?.takeIf { it != "-" }?.let { InfoRow("Full Name", it) }
                bio.placeOfBirth?.takeIf { it != "-" }?.let { InfoRow("Place of Birth", it) }
                bio.firstAppearance?.takeIf { it != "-" }?.let { InfoRow("First Appearance", it) }
                bio.publisher?.takeIf { it != "-" }?.let { InfoRow("Publisher", it) }
                bio.alignment?.takeIf { it != "-" }?.let { InfoRow("Alignment", it) }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
        Text(
            text = value,
            color = AppTheme.colors.text,
            style = AppTheme.typography.textMediumBold
        )
    }
}

@Composable
fun AppearanceSection(appearance: com.an9ar.jetheroes.data.dto.heroinfo.Appearance?) {
    appearance?.let { app ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            backgroundColor = AppTheme.colors.card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Appearance",
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.textMediumBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                app.gender?.takeIf { it != "-" }?.let { InfoRow("Gender", it) }
                app.race?.takeIf { it != "-" }?.let { InfoRow("Race", it) }
                app.height?.joinToString(", ")?.takeIf { it != "-" }?.let { InfoRow("Height", it) }
                app.weight?.joinToString(", ")?.takeIf { it != "-" }?.let { InfoRow("Weight", it) }
                app.eyeColor?.takeIf { it != "-" }?.let { InfoRow("Eye Color", it) }
                app.hairColor?.takeIf { it != "-" }?.let { InfoRow("Hair Color", it) }
            }
        }
    }
}

@Composable
fun WorkSection(work: com.an9ar.jetheroes.data.dto.heroinfo.Work?) {
    work?.let { w ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            backgroundColor = AppTheme.colors.card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Work",
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.textMediumBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                w.occupation?.takeIf { it != "-" }?.let { InfoRow("Occupation", it) }
                w.base?.takeIf { it != "-" }?.let { InfoRow("Base", it) }
            }
        }
    }
}

@Composable
fun ConnectionsSection(connections: com.an9ar.jetheroes.data.dto.heroinfo.Connections?) {
    connections?.let { conn ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            backgroundColor = AppTheme.colors.card
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Connections",
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.textMediumBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                conn.groupAffiliation?.takeIf { it != "-" }?.let { InfoRow("Group Affiliation", it) }
                conn.relatives?.takeIf { it != "-" }?.let { InfoRow("Relatives", it) }
            }
        }
    }
}
