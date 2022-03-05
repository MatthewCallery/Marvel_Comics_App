package com.marvel.comics.android.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.marvel.comics.android.R

@Composable
fun MainTopBar(actions: @Composable RowScope.() -> Unit = {}) {
    TopAppBar(
        elevation = dimensionResource(id = R.dimen.elevation_standard),
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = actions
    )
}
