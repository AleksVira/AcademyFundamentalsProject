package com.example.academyfundamentalsproject

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    val name: String = "",
    val number: Int = -1,
    var color: Int = Color.rgb(0, 0, 0)
): Parcelable