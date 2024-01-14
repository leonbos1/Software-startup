package com.example.app.data.repository

import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.remote.request.RegisterRequest
import com.example.app.util.Resource

interface UserRepository {

    suspend fun login(loginRequest: LoginRequest): Resource<Unit>
    suspend fun register(registerRequest: RegisterRequest): Resource<Unit>
    suspend fun logout(registerRequest: RegisterRequest): Resource<Unit>
}