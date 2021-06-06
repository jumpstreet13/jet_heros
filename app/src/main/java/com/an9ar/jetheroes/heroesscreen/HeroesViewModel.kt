package com.an9ar.jetheroes.heroesscreen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.an9ar.jetheroes.data.HeroRepository
import com.an9ar.jetheroes.data.dto.HeroResponse
import com.an9ar.jetheroes.data.source.HeroDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// todo @HiltViewModel
// todo inject constructor
class HeroesViewModel constructor(
    var movieRepository: HeroRepository
) : ViewModel() {

    val movies: Flow<PagingData<HeroResponse>> = Pager(PagingConfig(pageSize = 20)) {
        HeroDataSource(movieRepository)
    }.flow
}