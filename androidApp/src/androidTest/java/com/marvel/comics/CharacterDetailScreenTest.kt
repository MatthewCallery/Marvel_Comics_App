package com.marvel.comics

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.ui.screencharacterdetail.CharacterDetailScreen
import com.marvel.comics.android.ui.screencharacterdetail.ComicListTag
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

class CharacterDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository = MarvelRepositoryFake()
    private val viewModel = MarvelComicsViewModel(repository = repository)

    @ExperimentalCoilApi
    @ExperimentalCoroutinesApi
    @Test
    fun testComicList() {
        viewModel.loadCharacters()

        composeTestRule.setContent {
            CharacterDetailScreen(
                characterName = "Thor",
                viewModel = viewModel,
                popBack = {}
            )
        }

        val comicList = repository.getAllComics()
        val comicListNode = composeTestRule.onNodeWithTag(ComicListTag)

        composeTestRule.waitUntil(5000) {
            !viewModel.isLoadingComics.value
        }

        comicListNode
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(comicList.size)
    }
}
