package com.an9ar.jetheroes.data.dto.comicsinfo

import com.an9ar.jetheroes.data.dto.ThumbNailDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComicsDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("thumbnail")
    val thumbnail: ThumbNailDto
)