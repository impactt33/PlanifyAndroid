package com.example.planify.main.features.profiles.data.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchProfileRequestDTO (
    @SerialName("page")
    val page: Int,
    @SerialName("size")
    val size: Int,
    @SerialName("sort")
    val sort: List<String>,
)