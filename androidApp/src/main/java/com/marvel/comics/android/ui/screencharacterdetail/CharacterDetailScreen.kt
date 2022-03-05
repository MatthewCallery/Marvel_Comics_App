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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.ui.components.LoadingView
import com.marvel.comics.android.ui.components.ToolbarBackButton
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import com.marvel.comics.api.serializable.Comic
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val ComicListTag = "ComicList"

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun CharacterDetailScreen(
    characterName: String,
    viewModel: MarvelComicsViewModel,
    popBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.loadNewComicList(characterName = characterName)
    }

    val character = viewModel.getCharacter(characterName)
    val comics = viewModel.comics
    val isLoading = viewModel.isLoadingComics

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
                CharacterDetailHeaderView(character)
            }

            if (isLoading.value) {
                LoadingView()
            } else {
                ComicListUi(comics = comics)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ComicListUi(comics: SnapshotStateList<Comic>) {
    LazyColumn(modifier = Modifier.testTag(ComicListTag)) {
        items(comics) { comic ->
            ComicItemView(comic = comic)
        }
    }
}

