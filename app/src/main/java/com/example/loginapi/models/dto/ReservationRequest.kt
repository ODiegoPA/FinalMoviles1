package com.example.loginapi.models.dto

data class ReservationRequest(
    val restaurant_id: Int,
    val date: String,
    val time: String,
    val people: Int,
    val food: List<FoodItem>? = null
)
{

}