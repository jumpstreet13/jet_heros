package com.an9ar.jetheroes.data.dto.heroinfo

import com.an9ar.jetheroes.brandbook.DataViewModel
import com.an9ar.jetheroes.data.dto.ThumbNailDto
import com.an9ar.jetheroes.data.dto.getImageUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroResponse(
    @SerialName("name")
    val name: String,
    @SerialName("thumbnail")
    val thumbnail: ThumbNailDto,
    @SerialName("id")
    val id: Long
)

fun HeroResponse.toDataView(navigationLink: String): DataViewModel {
    return DataViewModel(
        title = name,
        imageUrl = thumbnail.getImageUrl(),
        navigationLink = navigationLink
    )
}