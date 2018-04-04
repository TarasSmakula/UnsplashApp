package com.gentledevs.unsplashapp.list

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.gentledevs.unsplashapp.R
import com.gentledevs.unsplashapp.SpaceItemDecoration
import com.gentledevs.unsplashapp.photo.PhotoActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel


class ListActivity : AppCompatActivity() {

    private val viewModel by viewModel<PhotoListViewModel>()

    private fun onPhotoClick(item: ImageItem, position: Int) {
        val intent = PhotoActivity.newIntent(this, item)
        val listItem = photoList.layoutManager.findViewByPosition(position)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, listItem as View, getString(R.string.transition_tag_photo))
        startActivity(intent, options.toBundle())
    }

    private val listAdapter = PhotoListAdapter {
        onItemClick { viewModel.onImageSelected(it) }
        onLoadMore { viewModel.onLoadMorePhotos() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intList()

        viewModel.images.observe(this, Observer { listAdapter.submitList(it) })
        viewModel.launchFullPhotoEvent.observe(this, Observer { it?.let { onPhotoClick(it.first, it.second) } })

        searchView.setOnQueryChangeListener({ _, newQuery ->
            viewModel.searForPhotos(newQuery)
        })

        viewModel.searForPhotos()
    }

    override fun onPause() {
        super.onPause()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    private fun intList() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        photoList.layoutManager = staggeredGridLayoutManager
        val itemDecoration = SpaceItemDecoration(this, R.dimen.item_offset)
        photoList.addItemDecoration(itemDecoration)
        photoList.adapter = listAdapter
    }
}
