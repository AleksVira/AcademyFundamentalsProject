package com.example.academyfundamentalsproject

import android.content.res.Resources

public fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
public fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
