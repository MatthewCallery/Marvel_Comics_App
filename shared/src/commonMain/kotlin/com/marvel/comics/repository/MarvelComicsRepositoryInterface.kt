package com.marvel.comics.repository

import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelComicsRepositoryInterface {

    suspend fun fetchAndStoreCharacters()
    suspend fun getAllCharacters(): List<MarvelCharacter>
    suspend fun getAllCharacters(term: String): List<MarvelCharacter>

    suspend fun fetchComics(characterId: Int, limit: Int, offset: Int)
    fun fetchComicsAsFlow(): Flow<List<Comic>>
    fun resetComicList()
}
