package com.an9ar.jetheroes.data.dto.heroinfo

import com.an9ar.jetheroes.brandbook.DataViewModel
import com.an9ar.jetheroes.data.dto.ThumbNailDto
import com.an9ar.jetheroes.data.dto.getImageUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroInfoDto(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnail")
    val thumbnail: ThumbNailDto,
    @SerialName("comics")
    val comicsDto: ComicsDto
)

fun HeroInfoDto.toDataViewModel(navigationUrl: String): DataViewModel {
    return DataViewModel(
        title = name,
        imageUrl = thumbnail.getImageUrl(),
        navigationLink = navigationUrl
    )
}