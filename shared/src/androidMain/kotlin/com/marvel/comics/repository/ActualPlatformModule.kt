package com.marvel.comics.repository

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.marvel.comics.dependencyinjection.MarvelDatabaseWrapper
import com.marvel.comics.database.MarvelDatabase
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = AndroidSqliteDriver(MarvelDatabase.Schema, get(), "marvelcomics.db")
        MarvelDatabaseWrapper(MarvelDatabase(driver))
    }
    single { Android.create() }
}
