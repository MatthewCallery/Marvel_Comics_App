package com.marvel.comics.android.ui.screencharacterdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.ui.components.ToolbarBackButton
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import org.koin.androidx.compose.getViewModel

const val ComicListTag = "ComicList"

@ExperimentalCoilApi
@Composable
fun CharacterDetailScreen(
    characterName: String,
    viewModel: MarvelComicsViewModel = getViewModel(),
    popBack: () -> Unit
) {

    val character = viewModel.getCharacter(characterName)
    val comicState = viewModel.characterComics.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.resetComicList()
    }

    character?.let {
        viewModel.updateComicList(characterId = character.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(characterName) },
                navigationIcon = { ToolbarBackButton(onClickBack = popBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            character?.let {
                CharacterDetailHeaderView(character = it)
            }

            LazyColumn(modifier = Modifier.testTag(ComicListTag)) {
                items(comicState.value) { comic ->
                    ComicItemView(comic = comic)
                }
            }
        }
    }
}

