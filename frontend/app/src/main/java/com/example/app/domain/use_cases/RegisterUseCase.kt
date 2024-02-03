package com.example.app.domain.use_cases


import android.util.Log
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
        password: String,
        address: String,
        addressNumber: Int,
        addressNumberAdd: String,
        city: String,
        postalCode: String
    ): RegisterResult {
        val firstNameError = if (firstName.isBlank()) "firstName cannot be blank" else null
        val lastNameError = if (lastName.isBlank()) "lastName cannot be blank" else null
        val emailError = if (email.isBlank()) "email cannot be blank" else null
        val phoneNumberError = if (phoneNumber.isBlank()) "phoneNumber cannot be blank" else null
        val userNameError = if (userName.isBlank()) "userName cannot be blank" else null
        val passwordError = if (password.isBlank()) "password cannot be blank" else null
        val addressError = if (address.isBlank()) "password cannot be blank" else null
        val cityError = if (address.isBlank()) "password cannot be blank" else null
        val postalCodeError = if (address.isBlank()) "password cannot be blank" else null

        val registerResult = RegisterResult()

        if (listOf(firstNameError, lastNameError, emailError, phoneNumberError, userNameError, passwordError, addressError, cityError, postalCodeError).any { it != null }) {
            registerResult.apply {
                this.firstNameError = firstNameError
                this.lastNameError = lastNameError
                this.emailError = emailError
                this.phoneNumberError = phoneNumberError
                this.userNameError = userNameError
                this.passwordError = passwordError
                this.addressError = addressError
                this.addressNumberError = addressNumberError
                this.cityError = cityError
                this.postalCodeError = postalCodeError
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
            address = address,
            addressNumber = addressNumber,
            addressNumberAddition = addressNumberAdd,
            city = city,
            postalCode = postalCode
        )
        Log.d("Tagg", addressNumberAdd)
        registerResult.result = repository.register(registerRequest)

        return registerResult
    }
}
