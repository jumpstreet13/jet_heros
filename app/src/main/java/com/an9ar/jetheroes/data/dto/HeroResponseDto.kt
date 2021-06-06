package com.an9ar.jetheroes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroResponseDto(
    @SerialName("copyright")
    val copyright: String,
    @SerialName("data")
    val pagingInfo: MarvelPagingDto
)