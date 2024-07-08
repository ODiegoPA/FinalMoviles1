package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.repositories.ReservationRepository

class RestaurantReservationViewModel : ViewModel() {
    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> get() = _reservations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchReservations(token: String, restaurantId: Int) {
        ReservationRepository.getReservationsByRestaurant(token, restaurantId,
            success = { reservations ->
                _reservations.value = reservations
            },
            failure = { error ->
                _error.value = "Error al obtener reservas: ${error.message}"
            }
        )
    }
}
