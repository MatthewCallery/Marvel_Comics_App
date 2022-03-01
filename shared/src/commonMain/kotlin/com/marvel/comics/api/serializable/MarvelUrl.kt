package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MarvelUrl(val type: String?, val url: String?)
