package com.marvel.comics.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.marvel.comics.android.navigation.Screen
import com.marvel.comics.android.navigation.addCharacterListScreen
import com.marvel.comics.android.theme.MarvelComicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainLayout()
        }
    }
}

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainLayout() {
    val navController = rememberAnimatedNavController()

    MarvelComicsTheme {
        Scaffold { paddingValues ->
            AnimatedNavHost(navController, startDestination = Screen.CharacterList.title) {
                this.addCharacterListScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
        }
    }
}
