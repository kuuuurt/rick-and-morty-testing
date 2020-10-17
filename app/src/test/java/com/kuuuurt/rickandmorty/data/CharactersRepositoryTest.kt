package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.data.remote.models.RickAndMortyInfo
import com.kuuuurt.rickandmorty.data.remote.models.RickAndMortyResponse
import com.kuuuurt.rickandmorty.domain.repositories.CharactersRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

abstract class CharactersRepositoryTest {
    abstract val charactersRepository: CharactersRepository
    abstract fun setupRepository()

    @Test
    fun getCharacters_shouldReturnCharacters() = runBlocking {
        // Given
        setupRepository()

        // When
        val actualCharacters = charactersRepository.getCharacters()

        // Then
        assertEquals(characters, actualCharacters)
    }
}

class FakeCharactersRepositoryTest : CharactersRepositoryTest() {
    override val charactersRepository: CharactersRepository
        get() = _charactersRepository

    private lateinit var _charactersRepository: FakeCharactersRepository

    override fun setupRepository() {
        _charactersRepository = FakeCharactersRepository()
    }
}

class RealCharactersRepositoryTest : CharactersRepositoryTest() {
    override val charactersRepository: CharactersRepository
        get() = _charactersRepository

    private lateinit var _charactersRepository: RealCharactersRepository

    val charactersResponse = Json.encodeToString(
        RickAndMortyResponse(RickAndMortyInfo(10, 1), characters)
    )

    override fun setupRepository() {
        _charactersRepository = RealCharactersRepository(
            HttpClient(
                mockEngine(
                    mapOf("https://rickandmortyapi.com/api/character" to charactersResponse)
                )
            ) {
                defaultRequest {
                    contentType(ContentType.Application.Json)
                }

                install(JsonFeature) {
                    accept(ContentType.Application.Json)
                    serializer = KotlinxSerializer()
                }
            }
        )
    }
}