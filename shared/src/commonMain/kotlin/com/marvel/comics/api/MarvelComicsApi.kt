package com.marvel.comics.api

import com.marvel.comics.BuildKonfig
import com.marvel.comics.api.serializable.MarvelCharacterDataWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.Clock
import okio.ByteString
import org.koin.core.component.KoinComponent

class MarvelComicsApi(
    private val client: HttpClient,
    private val baseUrl: String = "http://gateway.marvel.com/v1/public"
) : KoinComponent {

    private val publicKey = BuildKonfig.publicMarvelApiKey
    private val privateKey = BuildKonfig.privateMarvelApiKey

    suspend fun fetchCharacterDataWrapper(): MarvelCharacterDataWrapper {
        val timestamp = Clock.System.now().toEpochMilliseconds().toString()

        return client.get("$baseUrl/characters") {
            parameter("apikey", publicKey)
            parameter("ts", timestamp)
            parameter("hash", md5Hash(timestamp = timestamp))
        }.body()
    }

    private fun md5Hash(timestamp: String): String {
        val byte = "$timestamp$privateKey$publicKey".toByte()
        return ByteString.of(byte).md5().toString()
    }
}
