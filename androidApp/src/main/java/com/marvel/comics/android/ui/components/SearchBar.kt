package com.marvel.comics.android.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun SearchBar(
    search: String?,
    onSearch: (String?) -> Unit
) {
    when (search) {
        null -> MainTopBar(actions = { SearchIcon { onSearch("") } })
        else -> TopAppBar(
            title = { SearchTextField(search, onSearch) },
            navigationIcon = {
                BackHandler { onSearch(null) }
                ToolbarBackButton { onSearch(null) }
            },
            backgroundColor = MaterialTheme.colors.background
        )
    }
}
