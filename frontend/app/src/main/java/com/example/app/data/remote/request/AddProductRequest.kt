package com.example.app.data.remote.request

data class AddProductRequest(
    val description: String,
    val expiration_date: String,
    val name: String,
)