package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.models.dto.RestaurantRequest
import com.example.loginapi.repositories.RestaurantRepository

class CreateRestaurantViewModel : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadRestaurantDetails(restaurantId: Int) {
        RestaurantRepository.getRestaurantById(restaurantId,
            success = { restaurant ->
                _restaurant.value = restaurant
            },
            failure = { error ->
                _error.value = "Error al obtener detalles del restaurante: ${error.message}"
            }
        )
    }

    fun createRestaurant(token: String, restaurantRequest: RestaurantRequest) {
        RestaurantRepository.createRestaurant(token, restaurantRequest,
            success = {
                _success.value = true
            },
            failure = { error ->
                _error.value = "Error al crear restaurante: ${error.message}"
            }
        )
    }

    fun updateRestaurant(token: String, restaurantId: Int, restaurantRequest: RestaurantRequest) {
        RestaurantRepository.updateRestaurant(token, restaurantId, restaurantRequest,
            success = {
                _success.value = true
            },
            failure = { error ->
                _error.value = "Error al actualizar restaurante: ${error.message}"
            }
        )
    }

    fun deleteRestaurant(token: String, restaurantId: Int) {
        RestaurantRepository.deleteRestaurant(token, restaurantId,
            success = {
                _success.value = true
            },
            failure = { error ->
                _error.value = "Error al eliminar restaurante: ${error.message}"
            }
        )
    }
}
