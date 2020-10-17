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
class CharactersTestEspresso {
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

        launchFragmentInContainer<CharactersFragment>(themeResId = R.style.Theme_RickAndMorty)
            .onFragment { Navigation.setViewNavController(it.requireView(), navController) }
    }

    @Test
    fun shouldDisplayCharacters_onStart() {
        // When
        setupFragment()

        // Then
        characters.forEachIndexed { index, character ->
            onView(withId(R.id.rec_characters))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index))

            onView(withText(character.name)).check(matches(isDisplayed()))
            onView(withTagValue(`is`(character.image))).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayProgressBar_whenLoadingTakesLong() {
        // When
        setupFragment { simulateLoading = true }

        // Then
        onView(withId(R.id.prg_characters)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayEmptyMessage_whenLoadingFails() {
        // When
        setupFragment { simulateFailure = true }

        // Then
        onView(withId(R.id.empty_characters)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToCharacterDetails_onItemClick() {
        // Given
        setupFragment()

        // When
        onView(withId(R.id.rec_characters)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        // Then
        assertEquals(R.id.character_details, navController.currentDestination?.id)
    }
}