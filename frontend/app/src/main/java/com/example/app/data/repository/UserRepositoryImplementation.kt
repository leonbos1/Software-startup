package com.example.app.data.repository

import android.content.Context
import android.util.Log
import com.example.app.data.AuthToken
import com.example.app.data.remote.BackendApi
import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.remote.request.RegisterRequest
import com.example.app.data.remote.response.LogoutResponse
import com.example.app.data.remote.response.SafeUserResponse
import com.example.app.data.remote.response.UserResponse
import com.example.app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImplementation(
    private val backendApi: BackendApi,
    private val context: Context
): UserRepository {


    override suspend fun login(loginRequest: LoginRequest) : Resource<Unit> {
        return try {

            val response = backendApi.login(loginRequest)
            AuthToken.getInstance(context).token = response.token


            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }


    override suspend fun register(registerRequest: RegisterRequest): Resource<Unit> {
        return try {
            val response = backendApi.register(registerRequest)


            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }

    override suspend fun logout(): Flow<Resource<LogoutResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val productDetails = backendApi.logout(AuthToken.getInstance(context).token.toString())
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

    override suspend fun getUserDetails(userId: String): Flow<Resource<UserResponse>> {
        Log.d("tag", userId)
        return flow {
            try {
                emit(Resource.Loading())
                Log.d("tag", userId)
                val productDetails = backendApi.getUserDetails(AuthToken.getInstance(context).token.toString(),userId)
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

    override suspend fun getCurUser(): Flow<Resource<SafeUserResponse>> {
        return flow {
            try {
                emit(Resource.Loading())
                val productDetails = backendApi.getCurUser(AuthToken.getInstance(context).token.toString())
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