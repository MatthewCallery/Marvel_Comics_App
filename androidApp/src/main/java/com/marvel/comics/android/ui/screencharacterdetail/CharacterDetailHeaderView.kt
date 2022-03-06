package com.marvel.comics.android.ui.screencharacterdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.R
import com.marvel.comics.android.ui.components.CharacterImageView
import com.marvel.comics.android.ui.components.SpacerMedium
import com.marvel.comics.api.serializable.MarvelCharacter

@ExperimentalCoilApi
@Composable
fun CharacterDetailHeaderView(character: MarvelCharacter) {

    val characterName = character.name ?: ""
    val characterDescription = character.description ?: ""

    Surface(elevation = dimensionResource(id = R.dimen.elevation_standard)) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_m))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CharacterImageView(
                    characterImageUrl = character.imageUrl,
                    imageHeight = dimensionResource(id = R.dimen.image_size_large),
                    imageWidth = dimensionResource(id = R.dimen.image_size_large),
                    contentDescription = characterName
                )

                SpacerMedium()

                Text(text = characterName, style = MaterialTheme.typography.h1)
            }

            if (characterDescription.isNotEmpty()) {
                SpacerMedium()

                Text(
                    text = characterDescription,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
