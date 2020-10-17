package com.kuuuurt.rickandmorty.presentation

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.kuuuurt.rickandmorty.R
import com.kuuuurt.rickandmorty.ServiceLocator
import com.kuuuurt.rickandmorty.data.FakeEpisodesRepository
import com.kuuuurt.rickandmorty.data.character
import com.kuuuurt.rickandmorty.presentation.characterdetail.CharacterDetailsFragment
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hamcrest.core.Is.`is`
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
class CharacterDetailsTestBarista {
    private lateinit var episodesRepository: FakeEpisodesRepository

    private fun setupFragment(episodesRepository: FakeEpisodesRepository.() -> Unit = {}) {
        this.episodesRepository = FakeEpisodesRepository().apply(episodesRepository)
        ServiceLocator.episodesRepository = this.episodesRepository

        launchFragmentInContainer<CharacterDetailsFragment>(
            themeResId = R.style.Theme_RickAndMorty,
            fragmentArgs = bundleOf(
                "character" to Json.encodeToString(character)
            )
        )
    }

    @Test
    fun shouldDisplayCharacterDetails_onStart() {
        // When
        setupFragment()

        // Then
        assertDisplayed(character.name)
        assertDisplayed(character.species)
        assertDisplayed(character.status)
        assertDisplayed(character.location.name)
        assertDisplayed(character.origin.name)
        assertDisplayed(withTagValue(`is`(character.image)))
    }

    @Test
    fun shouldDisplayCharactersEpisodes_onStart() {
        // When
        setupFragment()

        // Then
        character.episodes.forEachIndexed { index, episode ->
            assertDisplayedAtPosition(R.id.rec_episodes, index, R.id.txt_episode, "E$episode")
            assertDisplayedAtPosition(R.id.rec_episodes, index, R.id.txt_title, "Episode $episode")
        }
    }


    @Test
    fun shouldDisplayProgressBar_whenLoadingTakesLong() {
        // When
        setupFragment { simulateLoading = true }

        // Then
        assertDisplayed(R.id.prg_episodes)
    }

    @Test
    fun shouldDisplayEmptyMessage_whenLoadingFails() {
        // When
        setupFragment { simulateFailure = true }

        // Then
        assertDisplayed(R.id.empty_episodes)
    }
}