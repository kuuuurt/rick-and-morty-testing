package com.kuuuurt.rickandmorty.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

@Serializable
data class Character(
    val id: Int,
    @SerialName("episode") val episodes: List<String>,
    val gender: String,
    val image: String,
    val location: Location,
    val origin: Location,
    val name: String,
    val species: String,
    val status: String,
    val type: String
)

@Serializable
data class Location(
    val name: String,
    val url: String
)