package com.kuuuurt.rickandmorty.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.kuuuurt.rickandmorty.R
import com.kuuuurt.rickandmorty.ServiceLocator
import com.kuuuurt.rickandmorty.data.FakeCharactersRepository
import com.kuuuurt.rickandmorty.data.characters
import com.kuuuurt.rickandmorty.presentation.characters.CharactersFragment
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertCustomAssertionAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

@MediumTest
@RunWith(AndroidJUnit4::class)
class CharactersTestBarista {
    private lateinit var charactersRepository: FakeCharactersRepository
    private lateinit var navController: TestNavHostController

    private fun setupFragment(charactersRepository: FakeCharactersRepository.() -> Unit = {}) {
        this.charactersRepository = FakeCharactersRepository().apply(charactersRepository)
        ServiceLocator.charactersRepository = this.charactersRepository

        navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            UiThreadStatement.runOnUiThread {
                setGraph(R.navigation.nav_main)
                setCurrentDestination(R.id.characters)
            }
        }

        launchFragmentInContainer<CharactersFragment>(
            themeResId = R.style.Theme_RickAndMorty
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
    }

    @Test
    fun shouldDisplayCharacters_onStart() {
        // When
        setupFragment()

        // Then
        characters.forEachIndexed { index, character ->
            scrollListToPosition(R.id.rec_characters, index)

            assertDisplayedAtPosition(R.id.rec_characters, index, R.id.txt_name, character.name)
            assertCustomAssertionAtPosition(
                R.id.rec_characters,
                index,
                R.id.img_character,
                matches(withTagValue(`is`(character.image)))
            )
        }

    }

    @Test
    fun shouldDisplayProgressBar_whenLoadingTakesLong() {
        // When
        setupFragment { simulateLoading = true }

        // Then
        assertDisplayed(R.id.prg_characters)
    }

    @Test
    fun shouldDisplayEmptyMessage_whenLoadingFails() {
        // When
        setupFragment { simulateFailure = true }

        // Then
        assertDisplayed(R.id.empty_characters)
    }

    @Test
    fun shouldNavigateToCharacterDetails_onItemClick() {
        // Given
        setupFragment()

        // When
        clickListItem(R.id.rec_characters, 0)

        // Then
        assertEquals(R.id.character_details, navController.currentDestination?.id)
    }
}