package com.example.academyfundamentalsproject.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MovieGridSpaceDecorator(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val half = space/2
        val index = parent.getChildLayoutPosition(view)
        val isLeft = (index % 2 == 0)
        outRect.set(
            if (isLeft) space else half,
            if (index in 0..1) 0 else half,
            if (isLeft) half else space,
            half
        )
    }
}

