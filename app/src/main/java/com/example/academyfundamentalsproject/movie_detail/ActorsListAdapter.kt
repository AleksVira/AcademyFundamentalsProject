package com.example.academyfundamentalsproject.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.data.ActorData
import com.example.academyfundamentalsproject.databinding.ViewHolderActorBinding

class ActorsListAdapter : RecyclerView.Adapter<ActorViewHolder>() {

    var actors: List<ActorData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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