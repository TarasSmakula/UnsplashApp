package com.gentledevs.unsplashapp.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


/**
 * Created by Taras Smakula on 2018-04-02.
 */
class PhotoListAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val onLoadMoreListener: () -> Unit

) : ListAdapter<ImageItem, PhotoViewHolder>(itemCallback()) {

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (position >= itemCount - ITEMS_OFFSET) {
            onLoadMoreListener()
        }
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(
            parent = parent,
            onItemClickListener = { position -> onItemClickListener.invoke(position) }
        )


    companion object {
        const val ITEMS_OFFSET = 6

        fun itemCallback() = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem) =
                oldItem == newItem
        }
    }

}