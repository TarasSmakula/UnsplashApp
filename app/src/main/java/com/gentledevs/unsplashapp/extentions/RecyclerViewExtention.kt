package com.gentledevs.unsplashapp.extentions

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Taras Smakula on 2018-04-02.
 */
val RecyclerView.ViewHolder.context: Context
    get() = itemView?.context
            ?: throw IllegalStateException("Accessing context of not yet initialized viewHolder: $this")

fun RecyclerView.Adapter<*>.inflate(@LayoutRes id: Int, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(id, parent, false)