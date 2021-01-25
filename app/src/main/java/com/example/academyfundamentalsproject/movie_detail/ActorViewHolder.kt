package com.example.academyfundamentalsproject.movie_detail

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.databinding.ViewHolderActorBinding

class ActorViewHolder(
    private val actorBinding: ViewHolderActorBinding,
) : RecyclerView.ViewHolder(actorBinding.root) {

    fun bindActor(actor: Actor) {
        with(actorBinding) {
            tvActorName.text = actor.name
            if (actor.imageUrl.isNotEmpty()) {
                ivActorPicture.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(root.context)
                    .load(actor.imageUrl)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .fallback(R.drawable.ic_avatar_placeholder)
                    .into(ivActorPicture)
            } else {
                ivActorPicture.scaleType = ImageView.ScaleType.FIT_START
                ivActorPicture.setImageResource(R.drawable.ic_avatar_placeholder)
            }
        }
    }

}