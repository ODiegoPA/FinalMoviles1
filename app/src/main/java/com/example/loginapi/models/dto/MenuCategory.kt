package com.example.loginapi.models.dto

data class MenuCategory(
    val id: Int,
    val name: String,
    val restaurant_id: Int,
    val plates: List<Plate>
)
