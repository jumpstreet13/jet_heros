package com.an9ar.jetheroes.data.dto.comicsinfo

import com.an9ar.jetheroes.brandbook.DataViewModel
import com.an9ar.jetheroes.data.dto.ThumbNailDto
import com.an9ar.jetheroes.data.dto.getImageUrl
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

fun ComicsDto.toDataViewModel(navLink: String): DataViewModel {
    return DataViewModel(
        title = title,
        imageUrl = thumbnail.getImageUrl(),
        navigationLink = navLink
    )
}