package com.example.app.data.remote.response

data class ProductResponse(
    val created: String,
    val description: String,
    val expiration_date: String,
    val id: String,
    val name: String,
    val updated: String,
    val user: User
)