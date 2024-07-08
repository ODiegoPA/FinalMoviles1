package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.Restaurant
import com.example.loginapi.repositories.RestaurantRepository

class MainMenuViewModel : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRestaurants() {
        RestaurantRepository.searchRestaurants(
            success = { restaurants ->
                _restaurants.value = restaurants
            },
            failure = { error ->
                _error.value = "Error al obtener restaurantes: ${error.message}"
            }
        )
    }

    fun fetchFilteredRestaurants(city: String?, selectedDate: String?, selectedTime: String?) {
        RestaurantRepository.searchRestaurantsFiltered(city, selectedDate, selectedTime,
            success = { restaurants ->
                _restaurants.value = restaurants
            },
            failure = { error ->
                _error.value = "Error al obtener restaurantes filtrados: ${error.message}"
            }
        )
    }
}
