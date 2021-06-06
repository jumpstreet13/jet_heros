package com.an9ar.jetheroes.heroesscreen

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.an9ar.jetheroes.data.HeroRepository

class HeroesViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: HeroRepository,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        return HeroesViewModel(repository) as T
    }
}