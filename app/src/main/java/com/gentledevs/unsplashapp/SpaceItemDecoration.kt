package com.gentledevs.unsplashapp

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by Taras Smakula on 2018-04-04.
 */
class SpaceItemDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int) : this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
    }
}