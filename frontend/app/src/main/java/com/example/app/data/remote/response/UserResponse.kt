package com.example.app.data.remote.response

data class UserResponse(
    val id : String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val username: String,
    val password: String,
    val address: String,
    val address_number: Int,
    val address_number_addition: String,
    val city: String,
    val postalCode: String
)
