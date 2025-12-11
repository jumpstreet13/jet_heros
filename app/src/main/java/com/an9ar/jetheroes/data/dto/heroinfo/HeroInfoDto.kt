package com.an9ar.jetheroes.data.dto.heroinfo

import com.an9ar.jetheroes.brandbook.DataViewModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroInfoDto(
    @SerialName("response")
    val response: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("powerstats")
    val powerstats: PowerStats? = null,
    @SerialName("biography")
    val biography: Biography? = null,
    @SerialName("appearance")
    val appearance: Appearance? = null,
    @SerialName("work")
    val work: Work? = null,
    @SerialName("connections")
    val connections: Connections? = null,
    @SerialName("image")
    val image: SuperHeroImage
) {
    fun getDescription(): String {
        val bio = biography
        return when {
            bio?.fullName != null && bio.fullName != "-" -> "Full Name: ${bio.fullName}\n"
            else -> ""
        } + when {
            bio?.placeOfBirth != null && bio.placeOfBirth != "-" -> "Place of Birth: ${bio.placeOfBirth}\n"
            else -> ""
        } + when {
            bio?.firstAppearance != null && bio.firstAppearance != "-" -> "First Appearance: ${bio.firstAppearance}\n"
            else -> ""
        } + when {
            bio?.publisher != null && bio.publisher != "-" -> "Publisher: ${bio.publisher}"
            else -> ""
        }.trim()
    }
}

@Serializable
data class PowerStats(
    @SerialName("intelligence")
    val intelligence: String? = null,
    @SerialName("strength")
    val strength: String? = null,
    @SerialName("speed")
    val speed: String? = null,
    @SerialName("durability")
    val durability: String? = null,
    @SerialName("power")
    val power: String? = null,
    @SerialName("combat")
    val combat: String? = null
)

@Serializable
data class Biography(
    @SerialName("full-name")
    val fullName: String? = null,
    @SerialName("alter-egos")
    val alterEgos: String? = null,
    @SerialName("aliases")
    val aliases: List<String>? = null,
    @SerialName("place-of-birth")
    val placeOfBirth: String? = null,
    @SerialName("first-appearance")
    val firstAppearance: String? = null,
    @SerialName("publisher")
    val publisher: String? = null,
    @SerialName("alignment")
    val alignment: String? = null
)

@Serializable
data class Appearance(
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("race")
    val race: String? = null,
    @SerialName("height")
    val height: List<String>? = null,
    @SerialName("weight")
    val weight: List<String>? = null,
    @SerialName("eye-color")
    val eyeColor: String? = null,
    @SerialName("hair-color")
    val hairColor: String? = null
)

@Serializable
data class Work(
    @SerialName("occupation")
    val occupation: String? = null,
    @SerialName("base")
    val base: String? = null
)

@Serializable
data class Connections(
    @SerialName("group-affiliation")
    val groupAffiliation: String? = null,
    @SerialName("relatives")
    val relatives: String? = null
)

fun HeroInfoDto.toDataViewModel(navigationUrl: String): DataViewModel {
    return DataViewModel(
        title = name,
        imageUrl = image.url,
        navigationLink = navigationUrl
    )
}