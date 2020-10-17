package com.kuuuurt.rickandmorty.presentation.characters

import androidx.lifecycle.viewModelScope
import com.kuuuurt.rickandmorty.data.FakeCharactersRepository
import com.kuuuurt.rickandmorty.data.characters
import com.kuuuurt.rickandmorty.domain.repositories.CharactersRepository
import com.kuuuurt.rickandmorty.presentation.CoroutineTestRule
import com.kuuuurt.rickandmorty.presentation.UiState
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

class CharactersViewModelTest {
    private lateinit var viewModel: CharactersViewModel
    private lateinit var charactersRepository: FakeCharactersRepository

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private fun setupViewModel(charactersRepository: FakeCharactersRepository.() -> Unit = {}) {
        this.charactersRepository = FakeCharactersRepository().apply(charactersRepository)
        viewModel = CharactersViewModel(this.charactersRepository)
    }

    @Test
    fun characters_shouldGetCharacters_onStart() = runBlockingTest {
        // When
        setupViewModel()

        // Then
        assertEquals(characters, viewModel.characters.first())
    }

    @Test
    fun getCharactersState_shouldBeLoading_onStart_ifRequestTakesLong() = runBlockingTest {
        // When
        setupViewModel { simulateLoading = true }

        // Then
        assertTrue(viewModel.getCharactersState.first() is UiState.Loading)
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun getCharactersState_shouldBeError_onStart_ifRequestFails() = runBlockingTest {
        // When
        setupViewModel { simulateFailure = true }

        // Then
        assertTrue(viewModel.getCharactersState.first() is UiState.Error)
    }

    @Test
    fun getCharactersState_shouldBeComplete_onStart_ifRequestSucceeds() = runBlockingTest {
        // When
        setupViewModel()

        // Then
        assertTrue(viewModel.getCharactersState.first() is UiState.Complete)
    }
}