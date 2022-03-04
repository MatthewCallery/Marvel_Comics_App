package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val id: Int,
    val title: String?,
    val thumbnail: String,
    val description: String
)
