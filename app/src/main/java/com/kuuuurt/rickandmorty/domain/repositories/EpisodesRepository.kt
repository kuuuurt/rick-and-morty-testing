package com.kuuuurt.rickandmorty.domain.repositories

import com.kuuuurt.rickandmorty.domain.entities.Episode

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

interface EpisodesRepository {
    suspend fun getEpisode(episodeId: Int): Episode
}