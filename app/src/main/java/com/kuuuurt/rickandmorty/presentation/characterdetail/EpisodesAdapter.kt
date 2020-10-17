package com.kuuuurt.rickandmorty.presentation.characterdetail

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
import com.kuuuurt.rickandmorty.databinding.ItemEpisodeBinding
import com.kuuuurt.rickandmorty.domain.entities.Character
import com.kuuuurt.rickandmorty.domain.entities.Episode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

class EpisodesAdapter : ListAdapter<Episode, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        ) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val episode = getItem(position)
        val viewBinding = ItemEpisodeBinding.bind(holder.itemView)
        viewBinding.txtEpisode.text = episode.episode
        viewBinding.txtTitle.text = episode.name
    }
}