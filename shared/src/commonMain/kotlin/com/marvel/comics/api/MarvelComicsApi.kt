package com.marvel.comics.api

import com.marvel.comics.BuildKonfig
import com.marvel.comics.api.serializable.ComicDataWrapper
import com.marvel.comics.api.serializable.MarvelCharacterDataWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

class MarvelComicsApi(
    private val client: HttpClient,
    private val baseUrl: String = "https://gateway.marvel.com/v1/public"
) : KoinComponent {

    suspend fun fetchCharacterDataWrapper(): MarvelCharacterDataWrapper =
        client.get("$baseUrl/characters") {
            addMarvelApiParams()
        }.body()

    suspend fun fetchComicDataWrapper(characterId: Int): ComicDataWrapper =
        client.get("$baseUrl/characters/$characterId/comics") {
            addMarvelApiParams()
        }.body()

    private fun HttpRequestBuilder.addMarvelApiParams() {
        val timestamp = generateTimestamp()

        parameter("apikey", BuildKonfig.publicMarvelApiKey)
        parameter("ts", timestamp)
        parameter("hash", md5Hash(timestamp = timestamp))
        parameter("limit", 100)
    }

    private fun generateTimestamp(): String = Clock.System.now().toEpochMilliseconds().toString()
}
