package com.marvel.comics.repository

import com.marvel.comics.dependencyinjection.MarvelDatabaseWrapper
import com.marvel.comics.dependencyinjection.commonModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class MarvelComicsRepositoryTest: KoinTest {

    private val repository: MarvelComicsRepositoryInterface by inject()

    @ExperimentalCoroutinesApi
    @BeforeTest
    fun setUp()  {
        Dispatchers.setMain(StandardTestDispatcher())

        startKoin{
            modules(
                commonModule(enableNetworkLogs = true, isTest = true),
                platformModule(),
                module {
                    single { MarvelDatabaseWrapper(null) }
                }
            )
        }
    }

    @AfterTest
    fun stop() {
        stopKoin()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testFetchAndStoreAllCharacters() = runTest {
        repository.fetchAndStoreAllCharacters()
        val result = repository.getAllCharacters()
        assertTrue(result.isNotEmpty())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchComics() = runTest {
        repository.fetchComics(characterId = 1010805, limit = 1, offset = 0)
        val result = repository.getAllComics()
        assertTrue(result.isNotEmpty())
    }
}
