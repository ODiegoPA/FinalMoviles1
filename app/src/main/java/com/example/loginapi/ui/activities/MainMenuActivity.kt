package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityMainMenuBinding
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.adapters.RestaurantAdapter
import com.example.loginapi.ui.viewmodels.MainMenuViewModel

class MainMenuActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantClickListener {
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var restaurantAdapter: RestaurantAdapter
    private val mainMenuViewModel: MainMenuViewModel by viewModels()
    companion object {
        const val FILTER_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupObservers()
        setupEventListeners()
        mainMenuViewModel.fetchRestaurants()
    }

    private fun setupRecyclerView() {
        restaurantAdapter = RestaurantAdapter(mutableListOf(), this)
        binding.lstRestaurants.layoutManager = LinearLayoutManager(this)
        binding.lstRestaurants.adapter = restaurantAdapter
    }

    private fun setupObservers() {
        mainMenuViewModel.restaurants.observe(this, Observer { restaurants ->
            restaurantAdapter.updateData(restaurants)
        })

        mainMenuViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, FilterMenuActivity::class.java)
            startActivityForResult(intent, FILTER_REQUEST_CODE)
        }

        binding.btnLogout.setOnClickListener {
            PreferencesRepository.clearToken(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            binding.btnMyReservations.visibility = View.VISIBLE
            binding.btnMyReservations.setOnClickListener {
                val intent = Intent(this, MyReservationsActivity::class.java)
                startActivity(intent)
            }
            binding.btnMyRestaurants.visibility = View.VISIBLE
            binding.btnMyRestaurants.setOnClickListener {
                val intent = Intent(this, MyRestaurantsActivity::class.java)
                startActivity(intent)
            }
            binding.btnGoToCreateRestaurant.visibility = View.VISIBLE
            binding.btnGoToCreateRestaurant.setOnClickListener {
                val intent = Intent(this, CreateRestaurantActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.btnMyReservations.visibility = View.GONE
            binding.btnMyRestaurants.visibility = View.GONE
            binding.btnGoToCreateRestaurant.visibility = View.GONE
            binding.btnLogout.text = "Iniciar Sesion"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILTER_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                val city = it.getStringExtra("city")
                val selectedDate = it.getStringExtra("selectedDate")
                val selectedTime = it.getStringExtra("selectedTime")

                mainMenuViewModel.fetchFilteredRestaurants(city, selectedDate, selectedTime)
            }
        }
    }

    override fun onRestaurantClick(restaurant: Restaurant) {
        val intent = Intent(this, RestaurantDetailActivity::class.java).apply {
            putExtra("restaurantId", restaurant.id)
        }
        startActivity(intent)
    }
}
