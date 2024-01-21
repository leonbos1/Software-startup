package com.example.app.data.remote

import com.example.app.data.remote.request.AddProductRequest
import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.remote.request.RegisterRequest
import com.example.app.data.remote.response.LoginResponse
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.remote.response.RegisterResponse
import okhttp3.internal.userAgent
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendApi {
    companion object {
        const val BASE_URL = "https://saveplate-yvl4k4opia-ez.a.run.app/"
    }

    @GET("/products")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("/products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: String): ProductResponse

    @POST("/products")
    suspend fun addProduct(@Body addProductRequest: AddProductRequest): ProductResponse

    @DELETE("/products/{productId}")
    suspend fun deleteProduct(@Path("productId") productId: String)

    @POST("/users")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse
    @POST("/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}