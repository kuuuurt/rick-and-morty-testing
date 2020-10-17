package com.kuuuurt.rickandmorty.data

import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.domain.entities.Episode
import com.kuuuurt.rickandmorty.domain.entities.Location

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/09/2020
 */

val characters = mutableListOf<Character>().apply {
    repeat(10) {
        add(
            Character(
                it,
                listOf(),
                "Male",
                "Image $it",
                Location("Location $it", "url"),
                Location("Origin $it", "url"),
                "Name $it",
                "Species $it",
                "Status $it",
                "Type $it"
            )
        )
    }
}

val character = Character(
    1,
    listOf("1", "3", "5", "7", "9", "10", "11"),
    "Male",
    "Image 1",
    Location("Location 1", "url"),
    Location("Origin 1", "url"),
    "Name 1",
    "Species 1",
    "Status 1",
    "Type 1"
)