package com.example.loginapi.models.dto

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val description: String,
    val user_id: Int,
    val logo: String,
    val owner: Owner,
    val photos: List<Photo> = emptyList()
)

data class Owner(
    val id: Int,
    val name: String,
    val email: String
)

