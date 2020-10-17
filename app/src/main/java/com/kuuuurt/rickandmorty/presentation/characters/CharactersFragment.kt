package com.kuuuurt.rickandmorty.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kuuuurt.rickandmorty.R
import com.kuuuurt.rickandmorty.databinding.FragCharactersBinding
import com.kuuuurt.rickandmorty.presentation.MainViewModelFactory
import com.kuuuurt.rickandmorty.presentation.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class CharactersFragment : Fragment(R.layout.frag_characters) {
    private val viewModel by viewModels<CharactersViewModel> { MainViewModelFactory() }

    private val charactersAdapter by lazy { CharactersAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewBinding = FragCharactersBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.recCharacters) { v, insets ->
            v.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }

        viewBinding.recCharacters.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            adapter = charactersAdapter
        }

        viewModel.characters
            .onEach { charactersAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        viewModel.getCharactersState
            .onEach {
                viewBinding.recCharacters.isVisible = it is UiState.Complete
                viewBinding.prgCharacters.isVisible = it is UiState.Loading
                viewBinding.emptyCharacters.isVisible = it is UiState.Error
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}