package com.example.app.data.remote.response

data class RegisterResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val userName: String,
    val password: String,
    val phoneNumber: String,
    val created : String,
    val updated : String
)