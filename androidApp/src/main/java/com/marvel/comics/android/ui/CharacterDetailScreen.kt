package com.marvel.comics.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import org.koin.androidx.compose.getViewModel

@ExperimentalCoilApi
@Composable
fun CharacterDetailScreen(characterName: String, popBack: () -> Unit) {

    val marvelComicsViewModel = getViewModel<MarvelComicsViewModel>()
    val character = marvelComicsViewModel.getCharacter(characterName)

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
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CharacterDetailHeader() {

}

