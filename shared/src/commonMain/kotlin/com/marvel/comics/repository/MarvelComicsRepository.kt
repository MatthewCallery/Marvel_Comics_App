package com.marvel.comics.repository

import co.touchlab.kermit.Logger
import com.marvel.comics.api.MarvelComicsApi
import com.marvel.comics.api.serializable.Comic
import com.marvel.comics.api.serializable.MarvelCharacter
import com.marvel.comics.dependencyinjection.MarvelDatabaseWrapper
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
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
    private val settings: Settings = Settings()

    init {
        coroutineScope.launch {
            if (daysSinceCharactersFetch() >= daysInWeek) {
                resetDaysSinceCharactersFetch()
                fetchAndStoreAllCharacters()
            }
        }
    }

    override suspend fun fetchAndStoreAllCharacters() {
        var characterOffset = 0
        var totalCharacters = 1

        while (characterOffset < totalCharacters) {
            try {
                val result = marvelComicsApi.fetchCharacterDataWrapper(
                    limit = characterLimit,
                    offset = characterOffset
                )
                result.data?.let { data ->
                    data.total?.let { totalCharacters = it }
                    data.results?.let { storeCharacters(characters = it) }
                }
            } catch (e: Exception) {
                clearDaysSinceCharactersFetch()
                logger.w(e) { "Exception during fetchAndStoreAllCharacters: $e" }
            }

            characterOffset += characterLimit
        }
    }

    override suspend fun storeCharacters(characters: List<MarvelCharacter>) {
        try {
            databaseQueries?.transaction {
                characters.forEach {
                    databaseQueries.insertCharacter(
                        id = it.id.toLong(),
                        name = it.name,
                        description = it.description,
                        image = "${it.thumbnail?.path}/standard_medium.${it.thumbnail?.extension}"
                    )
                }
            }
        } catch (e: Exception) {
            clearDaysSinceCharactersFetch()
            logger.w(e) { "Exception during storeCharacters: $e" }
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

    override fun getAllComics(): List<Comic> = comics

    override fun resetComicList() = comics.clear()

    private fun daysSinceCharactersFetch(): Int {
        val currentTimestamp = Clock.System.now().toEpochMilliseconds()
        val lastFetchTimestamp =
            settings.getLong(key = keyDaysSinceCharactersFetch, defaultValue = 0)
        val difference = currentTimestamp - lastFetchTimestamp
        return (difference / (milliInSecond * secondsInMinute * minsInHour * hoursInDay)).toInt()
    }

    private fun resetDaysSinceCharactersFetch() {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        settings.putLong(key = keyDaysSinceCharactersFetch, value = timestamp)
    }

    private fun clearDaysSinceCharactersFetch() = settings.remove(key = keyDaysSinceCharactersFetch)

    companion object {
        private const val characterLimit = 100
        private const val keyDaysSinceCharactersFetch = "DaysSinceCharactersFetch"
        private const val milliInSecond = 1000
        private const val secondsInMinute = 60
        private const val minsInHour = 60
        private const val hoursInDay = 24
        private const val daysInWeek = 7
    }
}
