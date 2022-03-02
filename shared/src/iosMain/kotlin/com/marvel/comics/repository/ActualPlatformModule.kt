package com.marvel.comics.repository

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.marvel.comics.dependencyinjection.MarvelDatabaseWrapper
import com.marvel.comics.database.MarvelDatabase
import io.ktor.client.engine.ios.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(MarvelDatabase.Schema, "peopleinspace.db")
        MarvelDatabaseWrapper(MarvelDatabase(driver))
    }
    single { Ios.create() }
}
