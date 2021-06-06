package com.an9ar.jetheroes.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThumbNailDto(
    @SerialName("extension")
    val extension: String,
    @SerialName("path")
    val path: String,
)