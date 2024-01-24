package com.example.app.data.remote.request

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val userName: String,
    val password: String,
    val address: String,
    val addressNumber: String,
    val city: String,
    val postalCode: String

)
