package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.RestaurantRepository

class RestaurantDetailViewModel : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRestaurantDetails(restaurantId: Int) {
        RestaurantRepository.getRestaurantById(restaurantId,
            success = { restaurant ->
                _restaurant.value = restaurant
            },
            failure = { error ->
                _error.value = "Error al obtener detalles del restaurante: ${error.message}"
            }
        )
    }
}
