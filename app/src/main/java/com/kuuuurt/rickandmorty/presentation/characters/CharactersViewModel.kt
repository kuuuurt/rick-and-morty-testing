package com.kuuuurt.rickandmorty.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuuuurt.rickandmorty.ServiceLocator
import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.domain.repositories.CharactersRepository
import com.kuuuurt.rickandmorty.presentation.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(listOf())
    val characters: Flow<List<Character>> get() = _characters

    private val _getCharactersState = MutableStateFlow<UiState>(UiState.Complete())
    val getCharactersState: Flow<UiState> get() = _getCharactersState

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _getCharactersState.value = UiState.Error(throwable)
        }) {
            _getCharactersState.value = UiState.Loading()
            _characters.value = charactersRepository.getCharacters()
            _getCharactersState.value = UiState.Complete()
        }
    }

    companion object {
        fun create() = CharactersViewModel(ServiceLocator.charactersRepository)
    }
}