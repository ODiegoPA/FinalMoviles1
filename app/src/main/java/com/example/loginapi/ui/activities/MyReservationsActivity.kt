package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapi.adapters.ReservationAdapter
import com.example.loginapi.databinding.ActivityMyReservationsBinding
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.MyReservationsViewModel

class MyReservationsActivity : AppCompatActivity(), ReservationAdapter.OnReservationClickListener {
    private lateinit var binding: ActivityMyReservationsBinding
    private lateinit var reservationAdapter: ReservationAdapter
    private val myReservationsViewModel: MyReservationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        myReservationsViewModel.reservations.observe(this, Observer { reservations ->
            reservationAdapter.updateData(reservations)
        })

        myReservationsViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchReservations() {
        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            myReservationsViewModel.fetchReservations(token)
        } else {
            Toast.makeText(this, "Por favor, inicie sesión para ver sus reservas", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onReservationClick(reservation: Reservation) {
        val intent = Intent(this, ReservationDetailActivity::class.java).apply {
            putExtra("reservationId", reservation.id)
        }
        startActivity(intent)
    }
}
