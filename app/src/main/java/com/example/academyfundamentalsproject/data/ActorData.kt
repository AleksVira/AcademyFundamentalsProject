package com.example.academyfundamentalsproject.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorData(
    val id: Int = -1,
    val name: String = "",
    val imageUrl: String = ""
) : Parcelable