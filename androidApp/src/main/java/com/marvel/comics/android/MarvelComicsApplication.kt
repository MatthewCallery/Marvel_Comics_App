package com.marvel.comics.android

import android.app.Application
import com.marvel.comics.android.dependencyinjection.appModule
import com.marvel.comics.dependencyinjection.initKoin
import org.koin.android.ext.koin.androidContext

class MarvelComicsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MarvelComicsApplication)
            modules(appModule)
        }
    }
}
