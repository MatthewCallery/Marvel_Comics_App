package com.marvel.comics.android.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.composable
import com.marvel.comics.android.ui.CharacterListScreen

private const val screenTransitionDuration = 1000

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addCharacterListScreen(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    this.composable(
        route = Screen.CharacterList.title,
        exitTransition = {
            slideOutHorizontally() + fadeOut(animationSpec = tween(screenTransitionDuration))
        },
        popEnterTransition = {
            slideInHorizontally()
        }
    ) {
        CharacterListScreen(
            paddingValues = paddingValues,
            onCharacterSelected = {
                navController.navigate(Screen.CharacterDetails.title + "/${it.name}")
            }
        )
    }
}
