package com.an9ar.jetheroes.heroesscreen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.data.source.HeroDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel
@Inject constructor(
    private var heroRepository: HeroRepository
) : ViewModel(), LifecycleObserver {

    val heroes: Flow<PagingData<HeroResponse>> = Pager(PagingConfig(pageSize = 20)) {
        HeroDataSource(heroRepository)
    }.flow
}