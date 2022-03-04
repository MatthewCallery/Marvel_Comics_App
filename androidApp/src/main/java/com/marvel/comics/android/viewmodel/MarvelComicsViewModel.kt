package com.marvel.comics.android.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.repository.MarvelComicsRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MarvelComicsViewModel(
    private val repository: MarvelComicsRepositoryInterface
) : ViewModel() {

    private var comicOffset = 0

    val marvelCharacters = repository.fetchCharactersAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val characterComics
        get() = repository.fetchComicsAsFlow()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getCharacter(characterName: String): MarvelCharacter? =
        marvelCharacters.value.find { it.name == characterName }

    fun resetComicList() {
        comicOffset = 0
        repository.resetComicList()
    }

    fun updateComicList(characterId: Int) {
        viewModelScope.launch {
            repository.fetchComics(
                characterId = characterId,
                limit = comicLimit,
                offset = comicOffset
            )
        }
    }

    companion object {
        private const val comicLimit = 20
    }
}
