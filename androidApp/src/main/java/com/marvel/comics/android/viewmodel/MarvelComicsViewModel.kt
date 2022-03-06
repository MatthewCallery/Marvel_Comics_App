package com.marvel.comics.android.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.repository.MarvelComicsRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MarvelComicsViewModel(
    private val repository: MarvelComicsRepositoryInterface
) : ViewModel() {

    private var comicOffset = 0

    /**
     * Character list params
     */

    private val _search = MutableStateFlow(null as String?)
    val search: StateFlow<String?> = _search

    private val _isLoadingCharacters = MutableStateFlow(true)
    val isLoadingCharacters: StateFlow<Boolean> = _isLoadingCharacters

    private val allCharacters = MutableStateFlow(listOf<MarvelCharacter>())
    private val searchCharacters = MutableStateFlow(listOf<MarvelCharacter>())

    @ExperimentalCoroutinesApi
    val characters: StateFlow<List<MarvelCharacter>> =
        search
            .flatMapLatest { search -> characters(search) }
            .stateInViewModel(initialValue = emptyList())

    /**
     * Comic list params
     */

    var isLoadingComics = mutableStateOf(true)
    var comics = mutableStateListOf<Comic>()

    /**
     * Character methods
     */

    fun loadCharacters() = effect {
        _isLoadingCharacters.value = true
        allCharacters.value = repository.getAllCharacters()
        _isLoadingCharacters.value = false
    }

    @ExperimentalCoroutinesApi
    fun getCharacter(characterName: String): MarvelCharacter? =
        allCharacters.value.find { it.name == characterName }

    fun search(term: String?) = effect {
        _search.value = term
        if (term != null) {
            searchCharacters.value = repository.getAllCharacters(term)
        }
    }

    private fun characters(search: String?) = when {
        search.isNullOrEmpty() -> allCharacters
        else -> searchCharacters
    }

    /**
     * Comic methods
     */

    fun loadNewComicList(characterName: String?) {
        isLoadingComics.value = true
        resetComicList()
        updateComicList(characterName = characterName)
    }

    private fun updateComicList(characterName: String?) = effect {
        val char = allCharacters.value.find { it.name == characterName }
        val charId = char?.id ?: return@effect

        repository.fetchComics(
            characterId = charId,
            limit = comicLimit,
            offset = comicOffset
        )
        comics.addAll(repository.getAllComics())
        isLoadingComics.value = false
    }

    private fun resetComicList() {
        comics.clear()
        repository.resetComicList()
    }

    /**
     * Helper methods
     */

    private fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> =
        stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = initialValue
        )

    private fun effect(block: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) { block() }

    companion object {
        private const val comicLimit = 100
    }
}
