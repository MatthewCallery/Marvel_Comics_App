package com.marvel.comics.android.ui.screencharacterlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.R
import com.marvel.comics.android.theme.MarvelComicsTheme
import com.marvel.comics.android.ui.components.CharacterImageView
import com.marvel.comics.api.serializable.MarvelCharacter

@ExperimentalCoilApi
@Composable
fun CharacterItemView(
    character: MarvelCharacter,
    onCharacterSelected: (character: MarvelCharacter) -> Unit
) {

    val characterName = character.name ?: stringResource(id = R.string.warning_name_not_found)
    val fallbackDescription =
        String.format(stringResource(id = R.string.warning_descript_not_found), characterName)
    val characterDescription =
        if (character.description.isNullOrEmpty()) fallbackDescription
        else character.description ?: fallbackDescription

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onCharacterSelected(character) })
            .padding(dimensionResource(id = R.dimen.padding_m)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CharacterImageView(
            characterImageUrl = character.imageUrl,
            imageHeight = dimensionResource(id = R.dimen.image_size_small),
            imageWidth = dimensionResource(id = R.dimen.image_size_small),
            contentDescription = characterName
        )

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_size_small)))

        Column {
            Text(text = characterName, style = MaterialTheme.typography.h2)
            Text(
                text = characterDescription,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
fun DefaultPreview(@PreviewParameter(CharacterProvider::class) character: MarvelCharacter) {
    MarvelComicsTheme {
        CharacterItemView(character, onCharacterSelected = {})
    }
}
