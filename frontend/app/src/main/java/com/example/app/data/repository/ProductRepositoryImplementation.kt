package com.example.app.data.repository

import android.content.Context
import android.util.Log
import com.example.app.data.AuthToken
import com.example.app.data.remote.BackendApi
import com.example.app.data.remote.request.AddProductRequest
import com.example.app.data.remote.response.ProductResponse
import com.example.app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImplementation(
    private val backendApi: BackendApi,
    private val context: Context
): ProductRepository {
    override suspend fun getProductsInRadius(radius: String): Flow<Resource<List<ProductResponse>>> {
        return flow {
            val productsFromBackendApi = try {
                backendApi.getProductsInRadius(AuthToken.getInstance(context).token.toString(), radius)
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
                val productDetails = backendApi.getProductDetails(AuthToken.getInstance(context).token.toString(), productId)
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

    override suspend fun addProduct(addProductRequest: AddProductRequest): Resource<Unit> {
        return try {
            backendApi.addProduct(AuthToken.getInstance(context).token.toString(), addProductRequest)
            Resource.Success(Unit)
        }catch (e: IOException){
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Resource.Error("${e.message}")
        }
    }

    override suspend fun deleteProduct(productId: String): Resource<Unit> {
        return try {
            backendApi.deleteProduct(AuthToken.getInstance(context).token.toString(), productId)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("Network error: Could not delete product")
        } catch (e: HttpException) {
            Resource.Error("HTTP error: Could not delete product")
        } catch (e: Exception) {
            Resource.Error("Unknown error occurred")
        }
    }
}