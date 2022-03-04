package com.marvel.comics.android.ui.screencharacterdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.R
import com.marvel.comics.android.ui.components.CharacterImageView
import com.marvel.comics.android.ui.components.SpacerMedium
import com.marvel.comics.api.serializable.Comic

@ExperimentalCoilApi
@Composable
fun ComicItemView(comic: Comic) {

    val comicTitle = comic.title ?: ""
    val comicDescription = comic.description ?: ""
    val comicImageUrl = comic.thumbnail?.let {
        "${it.path}/portrait_medium.${it.extension}"
    } ?: ""

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_m))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CharacterImageView(
                characterImageUrl = comicImageUrl,
                imageHeight = dimensionResource(id = R.dimen.image_comic_height),
                imageWidth = dimensionResource(id = R.dimen.image_comic_width),
                contentDescription = comicTitle
            )

            SpacerMedium()

            Text(text = comicTitle, style = MaterialTheme.typography.h1)
        }

        if (comicDescription.isNotEmpty()) {
            SpacerMedium()

            Text(
                text = comicDescription,
                style = MaterialTheme.typography.subtitle1.copy(color = Color.DarkGray)
            )
        }
    }
}
