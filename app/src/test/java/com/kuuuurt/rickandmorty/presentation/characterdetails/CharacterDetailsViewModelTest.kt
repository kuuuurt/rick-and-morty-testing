package com.kuuuurt.rickandmorty.presentation.characterdetails

import androidx.lifecycle.viewModelScope
import com.kuuuurt.rickandmorty.data.FakeEpisodesRepository
import com.kuuuurt.rickandmorty.data.character
import com.kuuuurt.rickandmorty.domain.repositories.EpisodesRepository
import com.kuuuurt.rickandmorty.presentation.CoroutineTestRule
import com.kuuuurt.rickandmorty.presentation.UiState
import com.kuuuurt.rickandmorty.presentation.characterdetail.CharacterDetailsViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

class CharacterDetailsViewModelTest {
    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var episodesRepository: EpisodesRepository

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private fun setupViewModel(episodesRepository: FakeEpisodesRepository.() -> Unit = {}) {
        this.episodesRepository = FakeEpisodesRepository().apply(episodesRepository)
        viewModel = CharacterDetailsViewModel(character, this.episodesRepository)
    }
    @Test
    fun shouldExposeCharacterDetails_onStart() = runBlockingTest {
        // When
        setupViewModel()

        // Then
        assertEquals(character.name, viewModel.name.first())
        assertEquals(character.species, viewModel.species.first())
        assertEquals(character.status, viewModel.status.first())
        assertEquals(character.type, viewModel.type.first())
        assertEquals(character.location.name, viewModel.location.first())
        assertEquals(character.origin.name, viewModel.origin.first())
        assertEquals(character.image, viewModel.imageUrl.first())
    }

    @Test
    fun shouldLoadEpisodes_onStart() = runBlockingTest {
        // When
        setupViewModel()

        // Then
        val episodes = viewModel.episodes.first()
        episodes.forEach {
            assertEquals("E${it.id}", it.episode)
            assertEquals("Episode ${it.id}", it.name)
        }
    }

    @Test
    fun loadEpisodesState_shouldBeLoading_onStart_whenRequestTakesLong() = runBlockingTest {
        // When
        setupViewModel { simulateLoading = true }

        // Then
        assertTrue(viewModel.loadEpisodesState.first() is UiState.Loading)
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun loadEpisodesState_shouldBeError_onStart_whenRequestFails() = runBlockingTest {
        // When
        setupViewModel { simulateFailure = true }

        // Then
        assertTrue(viewModel.loadEpisodesState.first() is UiState.Error)
    }

    @Test
    fun loadEpisodesState_shouldBeComplete_onStart_whenRequestSucceeds() = runBlockingTest {
        // When
        setupViewModel()

        // Then
        assertTrue(viewModel.loadEpisodesState.first() is UiState.Complete)
    }
}