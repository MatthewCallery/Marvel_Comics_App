package com.marvel.comics.android.ui.screencharacterlist

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.marvel.comics.api.serializable.MarvelCharacter

/**
 * Dummy data for the Jetpack Compose preview of CharacterItemView
 */
class CharacterProvider : CollectionPreviewParameterProvider<MarvelCharacter>(
    listOf(
        MarvelCharacter(
            id = 1,
            name = "Spiderman",
            description = "Friendly neighborhood Spiderman"
        ),
        MarvelCharacter(id = 2, name = "Thor", description = "God of Thunder")
    )
)
