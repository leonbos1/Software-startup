package com.example.app.domain.use_cases


import com.example.app.data.remote.request.RegisterRequest
import com.example.app.data.repository.UserRepository
import com.example.app.domain.results.RegisterResult


class RegisterUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        userName: String,
        password: String
    ): RegisterResult {
        val firstNameError = if (firstName.isBlank()) "firstName cannot be blank" else null
        val lastNameError = if (lastName.isBlank()) "lastName cannot be blank" else null
        val emailError = if (email.isBlank()) "email cannot be blank" else null
        val phoneNumberError = if (phoneNumber.isBlank()) "phoneNumber cannot be blank" else null
        val userNameError = if (userName.isBlank()) "userName cannot be blank" else null
        val passwordError = if (password.isBlank()) "password cannot be blank" else null

        val registerResult = RegisterResult()

        if (listOf(firstNameError, lastNameError, emailError, phoneNumberError, userNameError, passwordError).any { it != null }) {
            registerResult.apply {
                this.firstNameError = firstNameError
                this.lastNameError = lastNameError
                this.emailError = emailError
                this.phoneNumberError = phoneNumberError
                this.userNameError = userNameError
                this.passwordError = passwordError
            }
            return registerResult
        }

        val registerRequest = RegisterRequest(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            userName = userName,
            password = password,
        )

        registerResult.result = repository.register(registerRequest)

        return registerResult
    }
}
