package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(
    val id: Int,
    val name: String?,
    val description: String?,
    val resourceURI: String?,
    val urls: List<MarvelUrl>?,
    val thumbnail: MarvelImage?,
    val comics: ComicList?
)
