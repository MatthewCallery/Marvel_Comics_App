package com.marvel.comics.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.marvel.comics.android.R

@ExperimentalCoilApi
@Composable
fun CharacterImageView(
    characterImageUrl: String?,
    imageSize: Dp,
    contentDescription: String
) {
    val safeImageUrl = characterImageUrl ?: ""

    if (safeImageUrl.isNotEmpty()) {
        Image(
            painter = rememberImagePainter(safeImageUrl),
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.image_corner_radius)))
                .size(imageSize),
            contentDescription = contentDescription
        )
    } else {
        Spacer(modifier = Modifier.size(imageSize))
    }
}
