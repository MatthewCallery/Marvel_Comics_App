package com.marvel.comics.android.dependencyinjection

import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MarvelComicsViewModel(get()) }
}
