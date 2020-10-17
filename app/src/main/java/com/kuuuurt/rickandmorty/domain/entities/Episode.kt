package com.kuuuurt.rickandmorty.domain.entities

import kotlinx.serialization.Serializable

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

@Serializable
data class Episode(
    val id: Int,
    val episode: String,
    val name: String
)