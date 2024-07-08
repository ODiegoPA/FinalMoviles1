package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.repositories.ReservationRepository

class MyReservationsViewModel : ViewModel() {
    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> get() = _reservations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchReservations(token: String) {
        ReservationRepository.getReservations(token,
            success = { reservations ->
                _reservations.value = reservations
            },
            failure = { error ->
                _error.value = "Error al obtener reservas: ${error.message}"
            }
        )
    }
}
