package com.an9ar.jetheroes.data.dto.heroinfo

import com.an9ar.jetheroes.data.dto.ThumbNailDto
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