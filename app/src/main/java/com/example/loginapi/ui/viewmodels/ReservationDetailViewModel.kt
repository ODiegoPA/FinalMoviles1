package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.ReservationDetail
import com.example.loginapi.repositories.ReservationRepository

class ReservationDetailViewModel : ViewModel() {
    private val _reservationDetail = MutableLiveData<ReservationDetail>()
    val reservationDetail: LiveData<ReservationDetail> get() = _reservationDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _cancelSuccess = MutableLiveData<Boolean>()
    val cancelSuccess: LiveData<Boolean> get() = _cancelSuccess

    fun fetchReservationDetails(token: String, reservationId: Int) {
        ReservationRepository.getReservationById(token, reservationId,
            success = { reservationDetail ->
                _reservationDetail.value = reservationDetail
            },
            failure = { error ->
                _error.value = "Error al obtener detalles de la reserva: ${error.message}"
            }
        )
    }

    fun cancelReservation(token: String, reservationId: Int, isOwner: Boolean) {
        ReservationRepository.cancelReservation(token, reservationId,
            success = {
                _cancelSuccess.value = true
            },
            failure = { error ->
                _error.value = "Error al cancelar la reserva: ${error.message}"
            }
        )
    }
}
