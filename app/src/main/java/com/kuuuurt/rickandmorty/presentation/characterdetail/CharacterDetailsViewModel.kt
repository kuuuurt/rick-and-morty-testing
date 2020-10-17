package com.kuuuurt.rickandmorty.presentation.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuuuurt.rickandmorty.ServiceLocator
import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.domain.entities.Episode
import com.kuuuurt.rickandmorty.domain.repositories.EpisodesRepository
import com.kuuuurt.rickandmorty.presentation.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModel(
    private val character: Character,
    private val episodesRepository: EpisodesRepository
) : ViewModel() {
    private val _name = MutableStateFlow(character.name)
    val name: Flow<String> get() = _name

    private val _species = MutableStateFlow(character.species)
    val species: Flow<String> get() = _species

    private val _status = MutableStateFlow(character.status)
    val status: Flow<String> get() = _status

    private val _type = MutableStateFlow(character.type)
    val type: Flow<String> get() = _type

    private val _location = MutableStateFlow(character.location.name)
    val location: Flow<String> get() = _location

    private val _origin = MutableStateFlow(character.origin.name)
    val origin: Flow<String> get() = _origin

    private val _imageUrl = MutableStateFlow(character.image)
    val imageUrl: Flow<String> get() = _imageUrl

    private val _episodes = MutableStateFlow<List<Episode>>(listOf())
    val episodes: Flow<List<Episode>> get() = _episodes

    private val _loadEpisodesState = MutableStateFlow<UiState>(UiState.Loading())
    val loadEpisodesState: Flow<UiState> get() = _loadEpisodesState

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            _loadEpisodesState.value = UiState.Error(throwable)
        }) {
            _loadEpisodesState.value = UiState.Loading()
            _episodes.value = character.episodes
                .map { it.split("/").last().toInt() }
                .map { async { episodesRepository.getEpisode(it) } }
                .awaitAll()
            _loadEpisodesState.value = UiState.Complete()
        }
    }

    companion object {
        fun create(character: Character) = CharacterDetailsViewModel(
            character,
            ServiceLocator.episodesRepository
        )
    }
}