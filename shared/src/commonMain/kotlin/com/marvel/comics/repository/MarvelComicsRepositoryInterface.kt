package com.marvel.comics.repository

import com.marvel.comics.api.serializable.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelComicsRepositoryInterface {

    fun fetchCharactersAsFlow(): Flow<List<MarvelCharacter>>
    suspend fun fetchAndStoreCharacters()
}
