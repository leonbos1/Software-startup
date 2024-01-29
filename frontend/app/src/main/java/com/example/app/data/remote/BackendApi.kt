package com.example.app.data.remote

import com.example.app.data.remote.request.AddProductRequest
import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.remote.request.RegisterRequest
import com.example.app.data.remote.response.LoginResponse
import com.example.app.data.remote.response.LogoutResponse
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.remote.response.RegisterResponse
import com.example.app.data.remote.response.SafeUserResponse
import com.example.app.data.remote.response.UserResponse
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
    @GET("/products/radius")
    suspend fun getAllProducts(): List<ProductResponse>

    @GET("/products/radius/{radius}")
    suspend fun getProductsInRadius(@Header("Authorization") token: String, @Path("radius") radius: String): List<ProductResponse>

    @GET("/products/{productId}")
    suspend fun getProductDetails(@Header("Authorization") token: String, @Path("productId") productId: String): ProductResponse

    @POST("/products")
    suspend fun addProduct(@Header("Authorization") token: String, @Body addProductRequest: AddProductRequest): ProductResponse

    @DELETE("/products/{productId}")
    suspend fun deleteProduct(@Header("Authorization") token: String, @Path("productId") productId: String)

    @POST("/users")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/users/current_user")
    suspend fun  getCurUser(@Header("Authorization") token: String): SafeUserResponse

    @POST("/users/logout")
    suspend fun logout(@Header("Authorization") token: String): LogoutResponse

    @GET("/users/{userId}")
    suspend fun getUserDetails(@Header("Authorization") token: String, @Path("userId") productId: String): UserResponse

}