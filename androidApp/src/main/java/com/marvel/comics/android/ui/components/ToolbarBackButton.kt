package com.marvel.comics.android.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marvel.comics.android.R

@Composable
fun ToolbarBackButton(onClickBack: () -> Unit) {
    IconButton(onClick = { onClickBack() }) {
        Icon(
            Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.content_descript_back)
        )
    }
}
