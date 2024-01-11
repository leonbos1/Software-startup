package com.example.app.data.remote.request

data class AddProductRequest(
    val address: String,
    val city: String,
    val description: String,
    val email: String,
    val expiration_date: String,
    val first_name: String,
    val last_name: String,
    val name: String,
    val phone_number: String,
)