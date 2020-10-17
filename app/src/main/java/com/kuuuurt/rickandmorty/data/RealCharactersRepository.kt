package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.data.remote.models.RickAndMortyResponse
import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.domain.repositories.CharactersRepository
import io.ktor.client.*
import io.ktor.client.request.*

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class RealCharactersRepository(private val httpClient: HttpClient) : CharactersRepository {
    override suspend fun getCharacters() =
        httpClient
            .get<RickAndMortyResponse<Character>>("https://rickandmortyapi.com/api/character")
            .results
}