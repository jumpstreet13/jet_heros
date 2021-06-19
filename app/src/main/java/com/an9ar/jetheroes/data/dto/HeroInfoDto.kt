package com.an9ar.jetheroes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroInfoDto(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnail")
    val thumbnail: ThumbNailDto
)