package com.example.app.data.repository

import android.util.Log
import com.example.app.data.remote.BackendApi
import com.example.app.data.remote.response.ProductResponse
import com.example.app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductOverviewRepositoryImplementation(
    private val backendApi: BackendApi
): ProductOverviewRepository {

    override suspend fun getAllProducts(): Flow<Resource<List<ProductResponse>>> {
        return flow {
            val productsFromBackendApi = try {
                backendApi.getAllProducts()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Error loading products"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error loading products"))
                return@flow
            }

            emit(Resource.Success(productsFromBackendApi))
        }
    }

    override suspend fun getProductDetails(productId: String): Flow<Resource<ProductResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val productDetails = backendApi.getProductDetails(productId)
                Log.d("productDetails", productDetails.toString())
                emit(Resource.Success(productDetails))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Network error: Could not load product details"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("HTTP error: Could not load product details"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Unknown error occurred"))
            }
        }
    }
}