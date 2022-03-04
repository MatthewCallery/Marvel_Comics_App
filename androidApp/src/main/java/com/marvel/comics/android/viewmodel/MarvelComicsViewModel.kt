package com.marvel.comics.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.repository.MarvelComicsRepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MarvelComicsViewModel(
    repository: MarvelComicsRepositoryInterface
) : ViewModel() {

    val marvelCharacters = repository.fetchCharactersAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getCharacter(characterName: String): MarvelCharacter? =
        marvelCharacters.value.find { it.name == characterName }
}
