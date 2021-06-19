package com.an9ar.jetheroes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroInfoResponse(
    @SerialName("data")
    val info: HeroInfoWrapperDto
)