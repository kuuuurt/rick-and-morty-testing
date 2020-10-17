package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.domain.entities.Episode
import com.kuuuurt.rickandmorty.domain.repositories.EpisodesRepository
import io.ktor.client.*
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

abstract class EpisodesRepositoryTest {
    abstract val episodesRepository: EpisodesRepository
    abstract fun setupRepository()

    @Test
    fun getEpisode_shouldGetEpisode() = runBlocking {
        // Given
        setupRepository()

        // When
        val actualEpisode = episodesRepository.getEpisode(1)

        // Then
        assertEquals(actualEpisode.name, "Episode 1")
        assertEquals(actualEpisode.episode, "E1")
    }
}

class RealEpisodesRepositoryTest : EpisodesRepositoryTest() {
    override val episodesRepository: EpisodesRepository
        get() = realEpisodesRepository

    private lateinit var realEpisodesRepository: RealEpisodesRepository
    private val episodeResponse = Json.encodeToString(
        Episode(
            1,
            "E1",
            "Episode 1",
        )
    )

    override fun setupRepository() {
        realEpisodesRepository = RealEpisodesRepository(
            HttpClient(
                mockEngine(
                    mapOf(
                        "https://rickandmortyapi.com/api/episode/1" to episodeResponse
                    )
                )
            ) {
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
        )
    }
}

class FakeEpisodesRepositoryTest : EpisodesRepositoryTest() {
    override val episodesRepository: EpisodesRepository
        get() = fakeEpisodesRepository

    private lateinit var fakeEpisodesRepository: FakeEpisodesRepository

    override fun setupRepository() {
        fakeEpisodesRepository = FakeEpisodesRepository()
    }

}