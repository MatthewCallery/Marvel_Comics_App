package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MarvelImage(val path: String?, val extension: String?)
