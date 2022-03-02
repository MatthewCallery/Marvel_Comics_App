package com.marvel.comics.repository

import co.touchlab.kermit.Logger
import com.marvel.comics.api.MarvelComicsApi
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.dependencyinjection.MarvelDatabaseWrapper
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MarvelComicsRepository : KoinComponent, MarvelComicsRepositoryInterface {

    @NativeCoroutineScope
    private val coroutineScope: CoroutineScope = MainScope()

    private val marvelComicsApi: MarvelComicsApi by inject()
    private val database: MarvelDatabaseWrapper by inject()

    private val databaseQueries = database.instance?.marvelComicsQueries
    private val logger = Logger.withTag("PeopleInSpaceRepository")

    init {
        coroutineScope.launch {
            fetchAndStoreCharacters()
        }
    }

    override fun fetchCharactersAsFlow(): Flow<List<MarvelCharacter>> =
        databaseQueries?.selectAll(
            mapper = { id, name ->
                MarvelCharacter(id = id.toInt(), name = name)
            }
        )?.asFlow()?.mapToList() ?: flowOf(emptyList())

    override suspend fun fetchAndStoreCharacters() {
        try {
            val result = marvelComicsApi.fetchCharacterDataWrapper()

            databaseQueries?.transaction {
                result.data?.results?.forEach {
                    databaseQueries.insertCharacter(
                        id = it.id.toLong(),
                        name = it.name
                    )
                }
            }
        } catch (e: Exception) {
            logger.w(e) { "Exception during fetchAndStoreCharacters: $e" }
        }
    }
}
