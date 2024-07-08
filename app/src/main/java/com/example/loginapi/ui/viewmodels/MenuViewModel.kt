package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapi.models.dto.FoodItem
import com.example.loginapi.models.dto.MenuCategory
import com.example.loginapi.repositories.RestaurantRepository

class MenuViewModel : ViewModel() {
    private val _menuCategories = MutableLiveData<List<MenuCategory>>()
    val menuCategories: LiveData<List<MenuCategory>> get() = _menuCategories

    private val _selectedFoodItems = MutableLiveData<MutableList<FoodItem>>().apply { value = mutableListOf() }
    val selectedFoodItems: LiveData<MutableList<FoodItem>> get() = _selectedFoodItems

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchMenu(restaurantId: Int) {
        RestaurantRepository.getMenuById(restaurantId,
            success = { menuCategories ->
                _menuCategories.value = menuCategories
            },
            failure = { error ->
                _error.value = "Error al obtener el menú: ${error.message}"
            }
        )
    }

    fun selectFoodItem(foodItem: FoodItem, isSelected: Boolean) {
        _selectedFoodItems.value?.let { selectedFoodItems ->
            if (isSelected) {
                selectedFoodItems.add(foodItem)
            } else {
                selectedFoodItems.removeAll { it.plate_id == foodItem.plate_id }
            }
            _selectedFoodItems.value = selectedFoodItems
        }
    }
}
