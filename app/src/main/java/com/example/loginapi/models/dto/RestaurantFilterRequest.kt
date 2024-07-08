package com.example.loginapi.models.dto

data class RestaurantFilterRequest(
    val city: String?,
    val selectedDate: String?,
    val selectedTime: String?
)