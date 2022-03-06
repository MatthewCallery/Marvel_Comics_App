package com.marvel.comics

import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.repository.MarvelComicsRepositoryInterface

class MarvelRepositoryFake : MarvelComicsRepositoryInterface {

    private val characterList = listOf(
        MarvelCharacter(id = 1, name = "Thor"),
        MarvelCharacter(id = 2, name = "Zeus")
    )

    private val comicList = listOf(
        Comic(id = 1, title = "Comic Book 1", description = "This is Comic 1", thumbnail = null),
        Comic(id = 2, title = "Comic Book 2", description = "This is Comic 2", thumbnail = null)
    )

    override var isFetchingCharacters: Boolean = false

    override suspend fun fetchAndStoreAllCharacters() = Unit

    override fun getAllCharacters(): List<MarvelCharacter> = characterList

    override suspend fun getAllCharacters(term: String): List<MarvelCharacter> =
        characterList.filter { it.name!!.startsWith(term) }

    override suspend fun fetchComics(characterId: Int, limit: Int, offset: Int) = Unit

    override fun getAllComics(): List<Comic> = comicList

    override fun resetComicList() = Unit
}
