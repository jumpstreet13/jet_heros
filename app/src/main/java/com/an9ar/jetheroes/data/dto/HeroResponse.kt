package com.an9ar.jetheroes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroResponse(
    @SerialName("copyright")
    val copyright: String
)