package com.example.app.domain.use_cases


import com.example.app.data.remote.request.LoginRequest
import com.example.app.data.repository.UserRepository
import com.example.app.domain.results.LoginResult


class LoginUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(

        userName: String,
        password: String
    ): LoginResult {

        val userNameError = if (userName.isBlank()) "userName cannot be blank" else null
        val passwordError = if (password.isBlank()) "password cannot be blank" else null

        val loginResult = LoginResult()

        if (listOf(userNameError, passwordError).any { it != null }) {
            loginResult.apply {

                this.userNameError = userNameError
                this.passwordError = passwordError
            }
            return loginResult
        }

        val loginRequest = LoginRequest(

            userName = userName,
            password = password,
        )

        loginResult.result = repository.login(loginRequest)

        return loginResult
    }
}
