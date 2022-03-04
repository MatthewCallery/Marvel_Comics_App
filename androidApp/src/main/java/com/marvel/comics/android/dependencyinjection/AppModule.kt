package com.marvel.comics.android.dependencyinjection

import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Dependency injection for the Android app
 */
val appModule = module {
    viewModel { MarvelComicsViewModel(get()) }
}
