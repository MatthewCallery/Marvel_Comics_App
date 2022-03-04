package com.marvel.comics.android.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.marvel.comics.android.R

@Composable
fun SpacerMedium() {
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_m)))
}
