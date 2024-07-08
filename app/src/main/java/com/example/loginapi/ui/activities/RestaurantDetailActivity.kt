package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.loginapi.adapters.GalleryAdapter
import com.example.loginapi.databinding.ActivityRestaurantDetailBinding
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.RestaurantDetailViewModel

class RestaurantDetailActivity : AppCompatActivity(), GalleryAdapter.OnImageClickListener {
    private lateinit var binding: ActivityRestaurantDetailBinding
    private var restaurantId: Int = 0
    private val restaurantDetailViewModel: RestaurantDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantId = intent.getIntExtra("restaurantId", 0)

        if (restaurantId != 0) {
            restaurantDetailViewModel.fetchRestaurantDetails(restaurantId)
        }

        setupObservers()
        setupEventListeners()
    }

    private fun setupObservers() {
        restaurantDetailViewModel.restaurant.observe(this, Observer { restaurant ->
            restaurant?.let { bindRestaurantDetails(it) }
        })

        restaurantDetailViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        val token = PreferencesRepository.getToken(this)
        val isOwner = intent.getBooleanExtra("owner", false)
        if (token != null) {
            binding.btnViewReservations.visibility = View.VISIBLE
            binding.btnViewReservations.setOnClickListener {
                val intent = Intent(this, RestaurantReservationActivity::class.java).apply {
                    putExtra("restaurantId", restaurantId)
                }
                startActivity(intent)
            }
            binding.btnEditRestaurant.visibility = View.VISIBLE
            binding.btnEditRestaurant.setOnClickListener {
                val intent = Intent(this, CreateRestaurantActivity::class.java).apply {
                    putExtra("restaurantId", restaurantId)
                }
                startActivity(intent)
            }
        }
        if (!isOwner) {
            binding.btnViewReservations.visibility = View.GONE
            binding.btnEditRestaurant.visibility = View.GONE
        }
        binding.btnMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("restaurantId", restaurantId)
            }
            startActivity(intent)
        }

        binding.btnReserve.setOnClickListener {
            val token = PreferencesRepository.getToken(this)
            if (token != null) {
                val intent = Intent(this, ReservationActivity::class.java).apply {
                    putExtra("restaurantId", restaurantId)
                }
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("message", "Por favor, inicie sesión para realizar una reserva")
                startActivity(intent)
            }
        }
    }

    private fun bindRestaurantDetails(restaurant: Restaurant) {
        binding.apply {
            lblRestaurantName.text = restaurant.name
            lblRestaurantDescription.text = restaurant.description
            lblRestaurantCity.text = restaurant.city
            lblRestaurantDireccion.text = restaurant.address
            Glide.with(this@RestaurantDetailActivity).load(restaurant.logo).into(imgRestaurantLogo)

            val galleryImages = restaurant.photos.map { it.url }
            val galleryAdapter = GalleryAdapter(galleryImages, this@RestaurantDetailActivity)
            rvGallery.layoutManager = LinearLayoutManager(this@RestaurantDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvGallery.adapter = galleryAdapter
        }
    }

    override fun onImageClick(imageUrl: String) {
        val intent = Intent(this, FullScreenImageActivity::class.java).apply {
            putExtra(FullScreenImageActivity.EXTRA_IMAGE_URL, imageUrl)
        }
        startActivity(intent)
    }
}
