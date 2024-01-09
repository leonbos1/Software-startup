package com.example.app.data.repository

import com.example.app.data.remote.response.ProductResponse
import com.example.app.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductOverviewRepository {
    suspend fun getAllProducts(): Flow<Resource<List<ProductResponse>>>
    suspend fun getProductDetails(productId:String): Flow<Resource<ProductResponse>>
}