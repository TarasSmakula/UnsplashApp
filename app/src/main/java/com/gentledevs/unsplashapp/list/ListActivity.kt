package com.gentledevs.unsplashapp.list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gentledevs.unsplashapp.R
import com.gentledevs.unsplashapp.SpaceItemDecoration
import com.gentledevs.unsplashapp.databinding.ActivityMainBinding
import com.gentledevs.unsplashapp.photo.PhotoActivity
import org.koin.android.architecture.ext.viewModel


class ListActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModel<PhotoListViewModel>()
    private val binding by viewBinding(ActivityMainBinding::bind, R.id.container)

    private fun onPhotoClick(item: ImageItem, position: Int) {
        val intent = PhotoActivity.newIntent(this, item)
        val listItem = binding.photoList.layoutManager?.findViewByPosition(position)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            listItem as View,
            getString(R.string.transition_tag_photo)
        )
        startActivity(intent, options.toBundle())
    }

    private val listAdapter = PhotoListAdapter(
        onItemClickListener = { viewModel.onImageSelected(it) },
        onLoadMoreListener = { viewModel.onLoadMorePhotos() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intList()

        viewModel.images.observe(this) { listAdapter.submitList(it) }
        viewModel.launchFullPhotoEvent.observe(
            this
        ) { it?.let { onPhotoClick(it.first, it.second) } }

        binding.searchView.setOnQueryChangeListener { _, newQuery ->
            viewModel.searForPhotos(newQuery)
        }

        viewModel.searForPhotos()
    }

    override fun onPause() {
        super.onPause()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    private fun intList() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.photoList.layoutManager = staggeredGridLayoutManager
        val itemDecoration = SpaceItemDecoration(this, R.dimen.item_offset)
        binding.photoList.addItemDecoration(itemDecoration)
        binding.photoList.adapter = listAdapter
    }
}
