package com.example.app.data.repository

import com.example.app.data.remote.request.AddProductRequest
import com.example.app.data.remote.response.ProductResponse
import com.example.app.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(): Flow<Resource<List<ProductResponse>>>
    suspend fun getProductDetails(productId:String): Flow<Resource<ProductResponse>>

    suspend fun addProduct(addProductRequest: AddProductRequest): Resource<Unit>
    suspend fun deleteProduct(productId: String): Resource<Unit>
}