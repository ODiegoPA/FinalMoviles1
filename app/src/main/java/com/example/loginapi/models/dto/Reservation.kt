package com.example.loginapi.models.dto

data class Reservation(
    val id: Int,
    var restaurant: Restaurant?,  // Hacer nullable
    val date: String,
    val time: String,
    val people: Int,
    val food: List<FoodItem>?,
    val status: String
)

data class ReservationDetail(
    val id: Int,
    val restaurant: Restaurant,
    val date: String,
    val time: String,
    val people: Int,
    val food: List<FoodItem>?,
    val status: String
)
