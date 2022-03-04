package com.marvel.comics.android.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.R
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import com.marvel.comics.api.serializable.MarvelCharacter
import org.koin.androidx.compose.getViewModel

const val CharacterListTag = "CharacterList"

@ExperimentalCoilApi
@Composable
fun CharacterListScreen(
    onCharacterSelected: (character: MarvelCharacter) -> Unit,
    marvelComicsViewModel: MarvelComicsViewModel = getViewModel(),
    paddingValues: PaddingValues = PaddingValues()
) {

    val characterState = marvelComicsViewModel.marvelCharacters.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.title_screen_character_list)) })
        }
    ) {
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.testTag(CharacterListTag)) {
            items(characterState.value) { character ->
                CharacterItemView(character, onCharacterSelected)
            }
        }
    }
}
