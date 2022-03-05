package com.marvel.comics.android.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.marvel.comics.android.R

@Composable
fun SearchIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.content_descript_search)
            )
        }
    )
}
