package com.an9ar.jetheroes.data.dto.heroinfo

import com.an9ar.jetheroes.brandbook.DataViewModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroResponse(
    @SerialName("response")
    val response: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: SuperHeroImage
) {
    fun getIdAsLong(): Long = id.toLongOrNull() ?: 0L
}

@Serializable
data class SuperHeroImage(
    @SerialName("url")
    val url: String
)

fun HeroResponse.toDataView(navigationLink: String): DataViewModel {
    return DataViewModel(
        title = name,
        imageUrl = image.url,
        navigationLink = navigationLink
    )
}