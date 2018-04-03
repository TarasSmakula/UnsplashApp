package com.gentledevs.unsplashapp.list

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.gentledevs.unsplashapp.R
import com.gentledevs.unsplashapp.extentions.context
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_in_list.view.*

/**
 * Created by Taras Smakula on 2018-04-02.
 */

typealias OnItemClickListener = (adapterPosition: Int) -> Unit

class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var onItemClickListener: OnItemClickListener? = null
    private var image = view.image

    constructor(view: View, init: PhotoViewHolder.() -> Unit) : this(view) {
        apply(init)
    }

    init {
        view.setOnClickListener {
            adapterPosition
                    .takeIf { it != -1 }
                    ?.let { position -> onItemClickListener?.invoke(position) }
        }
    }

    fun onItemClick(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun bind(imageItem: ImageItem) {
        Picasso.with(context).load(imageItem.thumbImageUrl).fit().into(image)
    }

    companion object {
        @LayoutRes const val LAYOUT_RES = R.layout.item_image_in_list
    }
}