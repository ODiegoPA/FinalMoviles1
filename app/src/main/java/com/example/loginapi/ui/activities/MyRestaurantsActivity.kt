package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.databinding.ActivityMyRestaurantsBinding
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.adapters.RestaurantAdapter
import com.example.loginapi.ui.viewmodels.MyRestaurantsViewModel

class MyRestaurantsActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantClickListener {
    private lateinit var binding: ActivityMyRestaurantsBinding
    private lateinit var restaurantAdapter: RestaurantAdapter
    private val myRestaurantsViewModel: MyRestaurantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRestaurantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupEventListeners()

        fetchMyRestaurants()
    }

    private fun setupRecyclerView() {
        restaurantAdapter = RestaurantAdapter(mutableListOf(), this)
        binding.lstRestaurants.layoutManager = LinearLayoutManager(this)
        binding.lstRestaurants.adapter = restaurantAdapter
    }

    private fun setupObservers() {
        myRestaurantsViewModel.restaurants.observe(this, Observer { restaurants ->
            restaurantAdapter.updateData(restaurants)
        })

        myRestaurantsViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchMyRestaurants() {
        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            myRestaurantsViewModel.fetchMyRestaurants(token)
        } else {
            Toast.makeText(this, "Por favor, inicie sesión para ver sus restaurantes", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java).apply {
            putExtra("restaurantId", restaurant.id)
            putExtra("owner", true)
        }
        startActivity(intent)
    }
}
