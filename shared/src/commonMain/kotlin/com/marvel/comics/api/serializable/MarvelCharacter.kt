package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(
    val id: Int,
    val name: String? = null,
    val description: String? = null,
    val resourceURI: String? = null,
    val urls: List<MarvelUrl>? = null,
    val thumbnail: MarvelImage? = null,
    val comics: ComicList? = null,
    val imageUrl: String? = null
)
