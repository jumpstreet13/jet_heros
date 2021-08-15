package com.an9ar.jetheroes.heroesscreen

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.HeroInfoDto
import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.data.source.HeroDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel
@Inject constructor(
    private var heroRepository: HeroRepository
) : ViewModel(), LifecycleObserver {

    val heroes: Flow<PagingData<HeroResponse>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        HeroDataSource(heroRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun fetchHeroInfo(heroId: Long): GreatResult<HeroInfoDto> {
        return try {
            heroRepository.loadHeroInfoById(heroId)
        } catch (exception: Exception) {
            GreatResult.Error(exception)
        }
    }
}