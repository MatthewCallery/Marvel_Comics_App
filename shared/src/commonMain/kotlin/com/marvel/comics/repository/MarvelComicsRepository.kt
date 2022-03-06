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
    private val characters: MutableList<MarvelCharacter> = mutableListOf()
    private val comics: MutableList<Comic> = mutableListOf()
    private val logger = Logger.withTag("MarvelRepository")
    private val settings: Settings = Settings()

    override var isFetchingCharacters: Boolean = true

    init {
        coroutineScope.launch {
            if (daysSinceCharactersFetch() >= daysInWeek) {
                resetDaysSinceCharactersFetch()
                fetchAndStoreAllCharacters()
            } else {
                characters.addAll(loadCharactersFromDatabase())
            }
            isFetchingCharacters = false
        }
    }

    /**
     * Characters
     */

    override suspend fun fetchAndStoreAllCharacters() {
        var characterOffset = 0
        var totalCharacters = 1

        while (characterOffset < totalCharacters) {
            try {
                val result = marvelComicsApi.fetchCharacterDataWrapper(
                    limit = characterLimit,
                    offset = characterOffset
                )
                result.data?.total?.let { totalCharacters = it }
                result.data?.results?.let { listOfCharacters ->
                    updateCharacterList(chars = listOfCharacters)
                    storeCharactersInDatabase(chars = listOfCharacters)
                }
            } catch (e: Exception) {
                clearDaysSinceCharactersFetch()
                logger.w(e) { "Exception during fetchAndStoreAllCharacters: $e" }
            }

            characterOffset += characterLimit
        }
    }

    private fun updateCharacterList(chars: List<MarvelCharacter>) =
        chars.forEach {
            val imageUrl = "${it.thumbnail?.path}/standard_medium.${it.thumbnail?.extension}"
            if (!characters.contains(it)) characters.add(it.copy(imageUrl = imageUrl))
        }

    private fun storeCharactersInDatabase(chars: List<MarvelCharacter>) =
        try {
            databaseQueries?.transaction {
                chars.forEach {
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

    private fun loadCharactersFromDatabase(): List<MarvelCharacter> =
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

    override fun getAllCharacters(): List<MarvelCharacter> = characters

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

    /**
     * Comics
     */

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
