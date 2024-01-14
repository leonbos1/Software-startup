package com.example.app.data.remote.request

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val userName: String,
    val password: String

)
