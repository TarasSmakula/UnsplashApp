package com.gentledevs.unsplashapp.photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gentledevs.unsplashapp.R
import com.gentledevs.unsplashapp.list.ImageItem
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*
import android.support.v4.app.ActivityOptionsCompat




class PhotoActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {

        image.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true
        val thumbUrl = intent.getStringExtra(IMAGE_THUMB_URL)
        val fullUrl = intent.getStringExtra(IMAGE_FULL_URL)

        loadImageWithThumbPlaceholder(thumbUrl, fullUrl)

        image.setOnClickListener { toggle() }

    }


    private fun loadImageWithThumbPlaceholder(thumb: String, full: String) {
        Picasso.with(this)
                .load(thumb)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        Picasso.with(this@PhotoActivity)
                                .load(full)
                                .fit()
                                .noPlaceholder()
                                .into(image)
                    }

                    override fun onError() {

                    }
                })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {

        supportActionBar?.hide()
        mVisible = false

        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {

        image.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true


        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }


    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {

        private val UI_ANIMATION_DELAY = 300


        private val IMAGE_THUMB_URL = "thumb_url"
        private val IMAGE_FULL_URL = "full_url"

        fun newIntent(context: Context, image: ImageItem): Intent {
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra(IMAGE_THUMB_URL, image.thumbImageUrl)
            intent.putExtra(IMAGE_FULL_URL, image.fullImageUrl)
            return intent
        }
    }
}
