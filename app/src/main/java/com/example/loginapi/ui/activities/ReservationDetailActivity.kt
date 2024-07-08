package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.loginapi.databinding.ActivityReservationDetailBinding
import com.example.loginapi.models.dto.ReservationDetail
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.ReservationDetailViewModel


class ReservationDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailBinding
    private var reservationId: Int = 0
    private val reservationDetailViewModel: ReservationDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reservationId = intent.getIntExtra("reservationId", 0)
        val token = PreferencesRepository.getToken(this)
        val isOwner = intent.getBooleanExtra("owner", false)

        if (reservationId != 0 && token != null) {
            reservationDetailViewModel.fetchReservationDetails(token, reservationId)
        }

        setupObservers()
        setupEventListeners(token, isOwner)
    }

    private fun setupObservers() {
        reservationDetailViewModel.reservationDetail.observe(this, Observer { reservationDetail ->
            reservationDetail?.let { bindReservationDetails(it) }
        })

        reservationDetailViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })

        reservationDetailViewModel.cancelSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Reserva cancelada con éxito", Toast.LENGTH_SHORT).show()
                navigateAfterCancellation()
            }
        })
    }

    private fun setupEventListeners(token: String?, isOwner: Boolean) {
        binding.btnCancelReservation.setOnClickListener {
            token?.let {
                reservationDetailViewModel.cancelReservation(it, reservationId, isOwner)
            }
        }
    }

    private fun bindReservationDetails(reservationDetail: ReservationDetail) {
        binding.apply {
            lblRestaurantName.text = reservationDetail.restaurant.name
            lblReservationDate.text = reservationDetail.date
            lblReservationTime.text = reservationDetail.time
            lblReservationPeople.text = reservationDetail.people.toString()
            Glide.with(this@ReservationDetailActivity).load(reservationDetail.restaurant.logo).into(imgRestaurantLogo)

            val foodItems = reservationDetail.food?.joinToString("\n") { "${it.qty}x ${it.plate_id}" }
            lblFoodItems.text = foodItems ?: "No hay platos reservados"
        }
    }

    private fun navigateAfterCancellation() {
        val isOwner = intent.getBooleanExtra("owner", false)
        val intent = if (isOwner) {
            Intent(this, MainMenuActivity::class.java)
        } else {
            Intent(this, MyReservationsActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
