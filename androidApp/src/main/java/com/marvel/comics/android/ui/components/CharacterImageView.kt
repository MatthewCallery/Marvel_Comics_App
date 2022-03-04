package com.marvel.comics.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
    imageHeight: Dp,
    imageWidth: Dp,
    contentDescription: String
) {
    val safeImageUrl = characterImageUrl ?: ""

    if (safeImageUrl.isNotEmpty()) {
        Image(
            painter = rememberImagePainter(safeImageUrl),
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.image_corner_radius)))
                .height(imageHeight)
                .width(imageWidth),
            contentDescription = contentDescription
        )
    } else {
        Spacer(modifier = Modifier.size(imageHeight))
    }
}
