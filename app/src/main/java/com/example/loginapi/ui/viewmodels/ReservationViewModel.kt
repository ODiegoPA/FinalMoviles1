package com.example.loginapi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.FoodItem
import com.example.loginapi.models.dto.ReservationRequest
import com.example.loginapi.repositories.RestaurantRepository

class ReservationViewModel : ViewModel() {
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> get() = _time

    private val _selectedFoodItems = MutableLiveData<List<FoodItem>>()
    val selectedFoodItems: LiveData<List<FoodItem>> get() = _selectedFoodItems

    private val _reservationSuccess = MutableLiveData<Boolean>()
    val reservationSuccess: LiveData<Boolean> get() = _reservationSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun setDate(date: String) {
        _date.value = date
    }

    fun setTime(time: String) {
        _time.value = time
    }

    fun setSelectedFoodItems(foodItems: List<FoodItem>) {
        _selectedFoodItems.value = foodItems
    }

    fun createReservation(token: String, reservationRequest: ReservationRequest) {
        RestaurantRepository.createReservation(token, reservationRequest,
            success = {
                Log.d("Hola","Exito")
                _reservationSuccess.value = true
            },
            failure = { error ->
                _reservationSuccess.value = true
            })
    }
}
