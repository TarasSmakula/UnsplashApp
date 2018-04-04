package com.gentledevs.unsplashapp.list

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.gentledevs.unsplashapp.extentions.inflate

/**
 * Created by Taras Smakula on 2018-04-02.
 */
class PhotoListAdapter() : ListAdapter<ImageItem, PhotoViewHolder>(itemCallback()) {

    val ITEMS_OFFSET = 6

    private var onItemClickListener: OnItemClickListener = {}
    private var onLoadMoreListener: () -> Unit = {}

    constructor(init: PhotoListAdapter.() -> Unit) : this() {
        apply(init)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
       if(position >= itemCount - ITEMS_OFFSET){
           onLoadMoreListener()
       }
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = requestViewHolder(parent)


    private fun requestViewHolder(parent: ViewGroup): PhotoViewHolder =
            PhotoViewHolder(inflate(PhotoViewHolder.LAYOUT_RES, parent)) {
                onItemClick(onItemClickListener)
            }

    companion object {
        fun itemCallback() = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem) = oldItem == newItem
        }
    }

    fun onItemClick(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun onLoadMore(listener: () -> Unit) {
        onLoadMoreListener = listener
    }
}