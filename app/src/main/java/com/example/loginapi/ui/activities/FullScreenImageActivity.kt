package com.example.loginapi.ui.activities

import com.example.loginapi.ui.viewmodels.FullScreenImageViewModel
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.loginapi.databinding.ActivityFullScreenImageBinding

class FullScreenImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenImageBinding
    private val fullScreenImageViewModel: FullScreenImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        fullScreenImageViewModel.setImageUrl(imageUrl)

        fullScreenImageViewModel.imageUrl.observe(this, Observer { url ->
            url?.let {
                Glide.with(this).load(it).into(binding.imgFullScreen)
            }
        })

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_IMAGE_URL = "image_url"
    }
}
