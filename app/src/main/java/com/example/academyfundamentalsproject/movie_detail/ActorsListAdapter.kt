package com.example.academyfundamentalsproject.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.databinding.ViewHolderActorBinding

class ActorsListAdapter (private val actors: List<Actor>) : RecyclerView.Adapter<ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding =
            ViewHolderActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindActor(actors[position])
    }

    override fun getItemCount() = actors.size
}