package com.kuuuurt.rickandmorty.data.remote.models

import kotlinx.serialization.Serializable

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

@Serializable
data class RickAndMortyResponse<T>(
    val info: RickAndMortyInfo,
    val results: List<T>
)

@Serializable
data class RickAndMortyInfo(
    val count: Int,
    val pages: Int
)