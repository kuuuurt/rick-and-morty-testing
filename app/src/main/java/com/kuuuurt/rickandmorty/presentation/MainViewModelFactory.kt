package com.kuuuurt.rickandmorty.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.presentation.characterdetail.CharacterDetailsViewModel
import com.kuuuurt.rickandmorty.presentation.characters.CharactersViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class MainViewModelFactory(private val bundle: Bundle? = null) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharactersViewModel::class.java) -> {
                CharactersViewModel.create()
            }
            modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java) -> {
                val characterJson = requireNotNull(bundle?.getString("character")) {
                    "No character found."
                }
                val character = Json.decodeFromString<Character>(characterJson)
                CharacterDetailsViewModel.create(character)
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}