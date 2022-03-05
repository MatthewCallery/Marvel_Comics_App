package com.marvel.comics.android.ui.screencharacterlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.ui.components.LoadingView
import com.marvel.comics.android.ui.components.SearchBar
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import com.marvel.comics.api.serializable.MarvelCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val CharacterListTag = "CharacterList"

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun CharacterListScreen(
    onCharacterSelected: (character: MarvelCharacter) -> Unit,
    viewModel: MarvelComicsViewModel,
    paddingValues: PaddingValues = PaddingValues()
) {

    viewModel.loadCharacters()

    val characters by viewModel.characters.collectAsState()
    val search by viewModel.search.collectAsState()
    val isLoading by viewModel.isLoadingCharacters.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                search = search,
                onSearch = viewModel::search
            )
        }
    ) {
        if (isLoading) {
            LoadingView()
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.testTag(CharacterListTag)
            ) {
                items(characters) { character ->
                    CharacterItemView(character, onCharacterSelected)
                }
            }
        }
    }
}
