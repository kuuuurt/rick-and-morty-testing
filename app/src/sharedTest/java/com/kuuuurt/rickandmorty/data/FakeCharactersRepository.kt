package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.domain.repositories.CharactersRepository
import kotlinx.coroutines.delay

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

class FakeCharactersRepository : CharactersRepository {
    override suspend fun getCharacters(): List<Character> {
        simulateLoading()
        simulateFailure()
        return characters
    }

    var simulateLoading = false
    var simulateFailure = false

    private suspend fun simulateLoading() {
        if (simulateLoading) {
            delay(2000)
        }
    }

    private fun simulateFailure() {
        if (simulateFailure) {
            throw Exception("Forced Exception")
        }
    }
}