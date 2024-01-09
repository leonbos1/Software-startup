package com.example.app.data.remote

import com.example.app.data.remote.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BackendApi {
    companion object {
        const val BASE_URL = "http://10.0.2.2:5000"
    }

    @GET("/products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("/products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: String): ProductResponse
}