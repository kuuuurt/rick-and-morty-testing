package com.kuuuurt.rickandmorty.presentation.characterdetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.transition.MaterialContainerTransform
import com.kuuuurt.rickandmorty.R
import com.kuuuurt.rickandmorty.databinding.FragCharacterDetailsBinding
import com.kuuuurt.rickandmorty.databinding.FragCharactersBinding
import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.presentation.MainViewModelFactory
import com.kuuuurt.rickandmorty.presentation.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class CharacterDetailsFragment : Fragment(R.layout.frag_character_details) {
    private val viewModel by viewModels<CharacterDetailsViewModel> { MainViewModelFactory(arguments) }

    private val characterDetailsFragmentArgs by navArgs<CharacterDetailsFragmentArgs>()
    private val episodesAdapter by lazy { EpisodesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            containerColor = Color.WHITE
            duration = 500
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = Json.decodeFromString<Character>(characterDetailsFragmentArgs.character)
        val viewBinding = FragCharacterDetailsBinding.bind(view)
        ViewCompat.setTransitionName(viewBinding.root, "character${character.id}")

        viewBinding.recEpisodes.adapter = episodesAdapter

        viewModel.name
            .onEach { viewBinding.txtName.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.species
            .onEach { viewBinding.txtSpecies.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.status
            .onEach { viewBinding.txtStatus.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.type
            .onEach { viewBinding.txtType.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.location
            .onEach { viewBinding.txtLocation.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.origin
            .onEach { viewBinding.txtOrigin.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.imageUrl
            .onEach {
                viewBinding.imgCharacter.run {
                    load(it)
                    tag = it
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.episodes
            .onEach { episodesAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.loadEpisodesState
            .onEach {
                viewBinding.recEpisodes.isVisible = it is UiState.Complete
                viewBinding.prgEpisodes.isVisible = it is UiState.Loading
                viewBinding.emptyEpisodes.isVisible = it is UiState.Error
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}