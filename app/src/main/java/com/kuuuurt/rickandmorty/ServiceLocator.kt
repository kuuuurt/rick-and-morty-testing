package com.kuuuurt.rickandmorty

import com.kuuuurt.rickandmorty.data.RealCharactersRepository
import com.kuuuurt.rickandmorty.data.RealEpisodesRepository
import com.kuuuurt.rickandmorty.domain.repositories.CharactersRepository
import com.kuuuurt.rickandmorty.domain.repositories.EpisodesRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

object ServiceLocator {
    // Data
    private val httpClient = HttpClient {
        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(JsonFeature) {
            accept(ContentType.Application.Json)
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = true
                    encodeDefaults = false
                }
            )
        }
    }

    var charactersRepository: CharactersRepository = RealCharactersRepository(httpClient)
    var episodesRepository: EpisodesRepository = RealEpisodesRepository(httpClient)
}