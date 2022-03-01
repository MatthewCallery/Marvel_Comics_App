package com.marvel.comics.api.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacterDataWrapper(val data: MarvelCharacterDataContainer?)
