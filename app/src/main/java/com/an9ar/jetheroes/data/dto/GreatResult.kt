package com.an9ar.jetheroes.data.dto

sealed class GreatResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : GreatResult<T>()
    data class Error(val exception: Exception) : GreatResult<Nothing>()
    object Progress : GreatResult<Nothing>()
}