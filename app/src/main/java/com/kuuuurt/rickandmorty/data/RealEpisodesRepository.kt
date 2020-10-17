package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.domain.entities.Episode
import com.kuuuurt.rickandmorty.domain.repositories.EpisodesRepository
import io.ktor.client.*
import io.ktor.client.request.*

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class RealEpisodesRepository(private val httpClient: HttpClient) : EpisodesRepository {
    override suspend fun getEpisode(episodeId: Int): Episode {
        return httpClient.get("https://rickandmortyapi.com/api/episode/${episodeId}")
    }
}