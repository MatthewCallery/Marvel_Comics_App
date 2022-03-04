package com.marvel.comics.android.ui

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
import androidx.compose.ui.text.style.TextOverflow
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.R
import com.marvel.comics.api.serializable.MarvelCharacter

@ExperimentalCoilApi
@Composable
fun CharacterDetailHeaderView(character: MarvelCharacter) {

    val characterName = character.name ?: ""
    val characterDescription = character.description ?: ""

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CharacterImageView(
                characterImageUrl = character.imageUrl,
                imageSize = dimensionResource(id = R.dimen.image_size_large),
                contentDescription = characterName
            )

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_m)))

            Text(text = characterName, style = MaterialTheme.typography.h1)
        }

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_m)))

        if (characterDescription.isNotEmpty()) {
            Text(
                text = characterDescription,
                style = MaterialTheme.typography.subtitle1.copy(color = Color.DarkGray)
            )
        }
    }
}
