package com.kuuuurt.rickandmorty.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuuuurt.rickandmorty.R
import com.kuuuurt.rickandmorty.databinding.ItemCharacterBinding
import com.kuuuurt.rickandmorty.domain.entities.Character
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class CharactersAdapter : ListAdapter<Character, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        ) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = getItem(position)
        val viewBinding = ItemCharacterBinding.bind(holder.itemView)
        viewBinding.txtName.text = character.name
        viewBinding.imgCharacter.load(character.image)
        viewBinding.imgCharacter.tag = character.image
        ViewCompat.setTransitionName(viewBinding.root, "character${character.id}")
        viewBinding.root.setOnClickListener {
            it.findNavController().navigate(
                CharactersFragmentDirections.actionCharactersToCharacterDetails(
                    Json.encodeToString(character)
                ),
                FragmentNavigatorExtras(
                    viewBinding.root to viewBinding.root.transitionName
                )
            )
        }
    }
}