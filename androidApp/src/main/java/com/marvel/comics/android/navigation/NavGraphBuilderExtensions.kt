package com.marvel.comics.android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.composable
import com.marvel.comics.android.ui.screencharacterdetail.CharacterDetailScreen
import com.marvel.comics.android.ui.screencharacterlist.CharacterListScreen
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addCharacterListScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MarvelComicsViewModel
) {
    this.composable(
        route = Screen.CharacterList.title,
        exitTransition = { slideOutHorizontally() },
        popEnterTransition = { slideInHorizontally() }
    ) {
        CharacterListScreen(
            paddingValues = paddingValues,
            onCharacterSelected = {
                navController.navigate(Screen.CharacterDetails.title + "/${it.name}")
            },
            viewModel = viewModel
        )
    }
}

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addCharacterDetailScreen(
    navController: NavHostController,
    viewModel: MarvelComicsViewModel
) {
    this.composable(
        route = Screen.CharacterDetails.title + "/{character}",
        enterTransition = { slideInHorizontally() },
        popExitTransition = { slideOutHorizontally() }
    ) { backStackEntry ->
        CharacterDetailScreen(
            backStackEntry.arguments?.get("character") as String,
            popBack = { navController.popBackStack() },
            viewModel = viewModel
        )
    }
}
