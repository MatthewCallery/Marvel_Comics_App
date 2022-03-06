package com.marvel.comics.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
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
    val imageShape = RoundedCornerShape(dimensionResource(id = R.dimen.image_corner_radius))
    val imageModifier = Modifier
        .clip(imageShape)
        .height(imageHeight)
        .width(imageWidth)
        .border(
            width = dimensionResource(id = R.dimen.image_border_width),
            color = MaterialTheme.colors.onSurface,
            shape = imageShape
        )

    Image(
        painter = rememberImagePainter(
            safeImageUrl,
            builder = {
                placeholder(R.drawable.default_character)
            }
        ),
        modifier = imageModifier,
        contentDescription = contentDescription,
    )
}
