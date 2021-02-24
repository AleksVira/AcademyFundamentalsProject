package com.example.academyfundamentalsproject.common

import android.view.View
import java.lang.System.currentTimeMillis

fun View.setOnSingleClick(onClick: () -> Unit) {
    var lastClickTime = 0L
    setOnClickListener {
        if (currentTimeMillis() > lastClickTime + 700) onClick()
        lastClickTime = currentTimeMillis()
    }
}

fun View.setOnSingleViewClick(onClick: (View) -> Unit) {
    var lastClickTime = 0L
    setOnClickListener { view ->
        if (currentTimeMillis() > lastClickTime + 700) onClick(view)
        lastClickTime = currentTimeMillis()
    }
}

