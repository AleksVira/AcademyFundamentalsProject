package com.example.academyfundamentalsproject.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ActorsListSpaceDecorator(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val index = parent.getChildLayoutPosition(view)
        outRect.set(
            if (index == 0) 0 else space,
            space,
            0,
            space
        )
    }
}

