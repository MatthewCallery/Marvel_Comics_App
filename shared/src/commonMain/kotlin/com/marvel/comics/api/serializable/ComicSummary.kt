package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class ComicSummary(val resourceURI: String?, val name: String?)
