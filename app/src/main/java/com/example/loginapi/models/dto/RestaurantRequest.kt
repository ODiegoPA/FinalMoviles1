package com.example.loginapi.models.dto

data class RestaurantRequest(
    val name: String,
    val address: String,
    val city: String,
    val description: String
)
