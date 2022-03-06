package com.marvel.comics.repository

import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelComicsRepositoryInterface {

    var isFetchingCharacters: Boolean

    suspend fun fetchAndStoreAllCharacters()
    fun getAllCharacters(): List<MarvelCharacter>
    suspend fun getAllCharacters(term: String): List<MarvelCharacter>

    suspend fun fetchComics(characterId: Int, limit: Int, offset: Int)
    fun getAllComics(): List<Comic>
    fun resetComicList()
}
