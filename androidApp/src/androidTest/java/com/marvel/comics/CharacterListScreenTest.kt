package com.marvel.comics

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import coil.annotation.ExperimentalCoilApi
import com.marvel.comics.android.ui.screencharacterlist.CharacterListScreen
import com.marvel.comics.android.ui.screencharacterlist.CharacterListTag
import com.marvel.comics.android.viewmodel.MarvelComicsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

class CharacterListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository = MarvelRepositoryFake()
    private val viewModel = MarvelComicsViewModel(repository = repository)

    @ExperimentalCoilApi
    @ExperimentalCoroutinesApi
    @Test
    fun testCharacterListScreen() {
        composeTestRule.setContent {
            CharacterListScreen(
                onCharacterSelected = {},
                viewModel = viewModel
            )
        }

        val characterList = repository.getAllCharacters()
        val characterListNode = composeTestRule.onNodeWithTag(CharacterListTag)

        characterListNode
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(characterList.size)

        characterList.forEachIndexed { index, person ->
            val rowNode = characterListNode.onChildAt(index)
            rowNode.assertTextContains(person.name!!)
        }
    }
}
