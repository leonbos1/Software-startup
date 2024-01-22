package com.example.app.data.repository

import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.remote.request.RegisterRequest
import com.example.app.data.remote.response.LogoutResponse
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.remote.response.SafeUserResponse
import com.example.app.data.remote.response.UserResponse
import com.example.app.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(loginRequest: LoginRequest): Resource<Unit>
    suspend fun getCurUser(): Flow<Resource<SafeUserResponse>>
    suspend fun register(registerRequest: RegisterRequest): Resource<Unit>
    suspend fun logout(): Flow<Resource<LogoutResponse>>
    suspend fun getUserDetails(userId:String): Flow<Resource<UserResponse>>
}