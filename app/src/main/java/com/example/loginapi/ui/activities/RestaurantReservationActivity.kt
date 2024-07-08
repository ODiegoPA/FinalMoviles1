package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.adapters.ReservationAdapter
import com.example.loginapi.databinding.ActivityRestaurantReservationBinding
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.RestaurantReservationViewModel

class RestaurantReservationActivity : AppCompatActivity(), ReservationAdapter.OnReservationClickListener {
    private lateinit var binding: ActivityRestaurantReservationBinding
    private lateinit var reservationAdapter: ReservationAdapter
    private val restaurantReservationViewModel: RestaurantReservationViewModel by viewModels()
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantId = intent.getIntExtra("restaurantId", 0)

        setupRecyclerView()
        setupObservers()
        setupEventListeners()

        fetchReservations()
    }

    private fun setupRecyclerView() {
        reservationAdapter = ReservationAdapter(mutableListOf(), this)
        binding.lstReservations.layoutManager = LinearLayoutManager(this)
        binding.lstReservations.adapter = reservationAdapter
    }

    private fun setupObservers() {
        restaurantReservationViewModel.reservations.observe(this, Observer { reservations ->
            reservationAdapter.updateData(reservations)
        })

        restaurantReservationViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchReservations() {
        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            restaurantReservationViewModel.fetchReservations(token, restaurantId)
        }
    }

    override fun onReservationClick(reservation: Reservation) {
        val intent = Intent(this, ReservationDetailActivity::class.java).apply {
            putExtra("owner", true)
            putExtra("reservationId", reservation.id)
        }
        startActivity(intent)
    }
}
