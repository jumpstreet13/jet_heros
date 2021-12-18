package com.an9ar.jetheroes.data.dto.comicsinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicsWrapperDto(
    @SerialName("results")
    val results: List<ComicsDto>
)