package com.marvel.comics.repository

import co.touchlab.kermit.Logger
import com.marvel.comics.api.MarvelComicsApi
import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.dependencyinjection.MarvelDatabaseWrapper
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MarvelComicsRepository : KoinComponent, MarvelComicsRepositoryInterface {

    @NativeCoroutineScope
    private val coroutineScope: CoroutineScope = MainScope()

    private val marvelComicsApi: MarvelComicsApi by inject()
    private val database: MarvelDatabaseWrapper by inject()

    private val databaseQueries = database.instance?.marvelComicsQueries
    private val comics: MutableList<Comic> = mutableListOf()
    private val logger = Logger.withTag("MarvelRepository")

    init {
        coroutineScope.launch {
            fetchAndStoreCharacters()
        }
    }

    override suspend fun getAllCharacters(): List<MarvelCharacter> =
        databaseQueries?.selectAll(
            mapper = { id, name, description, image ->
                MarvelCharacter(
                    id = id.toInt(),
                    name = name,
                    description = description,
                    imageUrl = image
                )
            }
        )?.executeAsList() ?: emptyList()

    override suspend fun getAllCharacters(term: String): List<MarvelCharacter> =
        databaseQueries?.searchOnCharacter(
            searchQuery = "$term%",
            mapper = { id, name, description, image ->
                MarvelCharacter(
                    id = id.toInt(),
                    name = name,
                    description = description,
                    imageUrl = image
                )
            }
        )?.executeAsList() ?: emptyList()

    override suspend fun fetchAndStoreCharacters() {
        try {
            val result = marvelComicsApi.fetchCharacterDataWrapper()

            databaseQueries?.transaction {
                databaseQueries.deleteAll()
                result.data?.results?.forEach {
                    databaseQueries.insertCharacter(
                        id = it.id.toLong(),
                        name = it.name,
                        description = it.description,
                        image = "${it.thumbnail?.path}/standard_medium.${it.thumbnail?.extension}"
                    )
                }
            }
        } catch (e: Exception) {
            logger.w(e) { "Exception during fetchAndStoreCharacters: $e" }
        }
    }

    override suspend fun fetchComics(characterId: Int, limit: Int, offset: Int) {
        try {
            val result = marvelComicsApi.fetchComicDataWrapper(
                characterId = characterId,
                limit = limit,
                offset = offset
            )
            result.data?.results?.forEach {
                if (!comics.contains(it)) comics.add(it)
            }
        } catch (e: Exception) {
            logger.w(e) { "Exception during fetchComics: $e" }
        }
    }

    override fun fetchComicsAsFlow(): Flow<List<Comic>> {
        return flow { emit(comics.toList()) }
    }

    override fun resetComicList() = comics.clear()
}
