package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.domain.entities.Episode
import com.kuuuurt.rickandmorty.domain.repositories.EpisodesRepository
import kotlinx.coroutines.delay

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

class FakeEpisodesRepository : EpisodesRepository {
    override suspend fun getEpisode(episodeId: Int): Episode {
        simulateLoading()
        simulateFailure()
        return Episode(
            episodeId,
            "E$episodeId",
            "Episode $episodeId"
        )
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