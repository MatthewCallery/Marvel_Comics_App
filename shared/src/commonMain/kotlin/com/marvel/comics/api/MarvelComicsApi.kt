package com.marvel.comics.api

import com.marvel.comics.BuildKonfig
import com.marvel.comics.api.serializable.MarvelCharacterDataWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

class MarvelComicsApi(
    private val client: HttpClient,
    private val baseUrl: String = "https://gateway.marvel.com/v1/public"
) : KoinComponent {

    suspend fun fetchCharacterDataWrapper(): MarvelCharacterDataWrapper {
        val timestamp = Clock.System.now().toEpochMilliseconds().toString()

        return client.get("$baseUrl/characters") {
            parameter("apikey", BuildKonfig.publicMarvelApiKey)
            parameter("ts", timestamp)
            parameter("hash", md5Hash(timestamp = timestamp))
            parameter("limit", 100)
        }.body()
    }
}
