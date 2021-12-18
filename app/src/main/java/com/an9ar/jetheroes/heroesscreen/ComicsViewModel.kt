package com.an9ar.jetheroes.heroesscreen

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.an9ar.jetheroes.data.dto.GreatResult
import com.an9ar.jetheroes.data.dto.comicsinfo.ComicsWrapperDto
import com.an9ar.jetheroes.data.repository.ComicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel
@Inject constructor(
    private val comicsRepository: ComicsRepository
) : ViewModel(), LifecycleObserver {

    suspend fun fetchComicsInfo(url: String): GreatResult<ComicsWrapperDto> {
        return try {
            comicsRepository.loadComics(url)
        } catch (exception: Exception) {
            GreatResult.Error(exception)
        }
    }
}