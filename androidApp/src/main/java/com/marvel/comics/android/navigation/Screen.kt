package com.marvel.comics.android.navigation

sealed class Screen(val title: String) {
    object CharacterList: Screen(characterListScreen)
    object CharacterDetails: Screen(characterDetailScreen)

    companion object {
        private const val characterListScreen = "CharacterList"
        private const val characterDetailScreen = "CharacterDetail"
    }
}
