package com.example.app.data.repository

import android.content.Context
import com.example.app.data.AuthToken
import com.example.app.data.remote.BackendApi
import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.remote.request.RegisterRequest
import com.example.app.util.Resource
import retrofit2.HttpException
import java.io.File
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

    override suspend fun logout(registerRequest: RegisterRequest): Resource<Unit> {
        File("somefile.txt").writeText("")
        return try {
            val response = backendApi.register(registerRequest)


            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }

}