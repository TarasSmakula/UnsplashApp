package com.gentledevs.unsplashapp.list

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.gentledevs.unsplashapp.R
import com.gentledevs.unsplashapp.databinding.ItemImageBinding
import com.gentledevs.unsplashapp.extentions.calculateHeight
import com.gentledevs.unsplashapp.extentions.context
import com.gentledevs.unsplashapp.extentions.dimen
import com.squareup.picasso.Picasso


/**
 * Created by Taras Smakula on 2018-04-02.
 */

typealias OnItemClickListener = (adapterPosition: Int) -> Unit


interface ViewBindingHolder {

    val baseBinding: ViewBinding get() = viewBinding

    fun inflate(
        parent: ViewGroup,
        inflate: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
    ): View {
        viewBinding = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return viewBinding.root
    }

    private companion object {
        private lateinit var viewBinding: ViewBinding
    }
}

val RecyclerView.ViewHolder.context: Context get() = this.itemView.context

class PhotoViewHolder(
    parent: ViewGroup,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(inflate(parent, ItemImageBinding::inflate)) {

    private companion object : ViewBindingHolder

    private val binding = baseBinding as ItemImageBinding


    private var image = binding.image


    init {
        itemView.setOnClickListener {
            bindingAdapterPosition
                .takeIf { it != -1 }
                ?.let { position -> onItemClickListener.invoke(position) }
        }
    }

    fun bind(imageItem: ImageItem) {
        val photoWidth =
            (Resources.getSystem().displayMetrics.widthPixels / 2) - (context.dimen(R.dimen.item_offset) * 1.5).toInt()
        val photoHeight = imageItem.calculateHeight(photoWidth)

        image.layoutParams.height = photoHeight
        Picasso.with(context).load(imageItem.thumbImageUrl).into(image)
    }

}