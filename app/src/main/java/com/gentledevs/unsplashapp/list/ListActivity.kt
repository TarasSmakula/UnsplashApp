package com.gentledevs.unsplashapp.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gentledevs.unsplashapp.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel


class ListActivity : AppCompatActivity() {

    private val viewModel by viewModel<PhotoListViewModel>()

    private val listAdapter = PhotoListAdapter {
        onItemClick { viewModel.onImageSelected(it) }
        onLoadMore { viewModel.onLoadMorePhotos() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoList.adapter = listAdapter

        viewModel.images.observe(this, Observer { listAdapter.submitList(it) })

        searchView.setOnQueryChangeListener({ _, newQuery ->
            viewModel.searForPhotos(newQuery)
        })
    }
}
