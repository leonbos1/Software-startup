package com.example.app.data.remote.request

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val userName: String,
    val password: String,
    val address: String,
    val addressNumber: Int,
    val addressNumberAddition: String,
    val city: String,
    val postalCode: String

)
