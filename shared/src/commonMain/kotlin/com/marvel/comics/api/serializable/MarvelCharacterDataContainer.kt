package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacterDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<MarvelCharacter>?
)
