package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class ComicList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<ComicSummary>?
)
