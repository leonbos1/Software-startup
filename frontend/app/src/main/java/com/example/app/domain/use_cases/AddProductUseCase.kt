package com.example.app.domain.use_cases

import com.example.app.data.remote.request.AddProductRequest
import com.example.app.data.repository.ProductRepository
import com.example.app.domain.results.AddProductResult

class AddProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        phoneNumber: String,
        firstName: String,
        lastName: String,
        email: String,
        address: String,
        city: String,
        expirationDate: String
    ): AddProductResult {
        val nameError = if (name.isBlank()) "Name cannot be blank" else null
        val descriptionError = if (description.isBlank()) "Description cannot be blank" else null
        val phoneNumberError = if (phoneNumber.isBlank()) "Phone number cannot be blank" else null
        val firstNameError = if (firstName.isBlank()) "Firstname cannot be blank" else null
        val lastNameError = if (lastName.isBlank()) "lastname cannot be blank" else null
        val emailError = if (email.isBlank()) "Email cannot be blank" else null
        val addressError = if (address.isBlank()) "Address cannot be blank" else null
        val cityError = if (city.isBlank()) "City cannot be blank" else null
        val expirationDateError = if (expirationDate.isBlank()) "Address cannot be blank" else null

        val addProductResult = AddProductResult()

        if (listOf(nameError, descriptionError, phoneNumberError, firstNameError, lastNameError, emailError, addressError, cityError, expirationDateError).any { it != null }) {
            addProductResult.apply {
                this.nameError = nameError
                this.descriptionError = descriptionError
                this.phoneNumberError = phoneNumberError
                this.firsNameError = firstNameError
                this.lastNameError = lastNameError
                this.emailError = emailError
                this.addressError = addressError
                this.cityError = cityError
                this.expirationDateError = expirationDateError
            }
                return addProductResult
        }

        val addProductRequest = AddProductRequest(
            name = name,
            description = description,
            phone_number = phoneNumber,
            first_name = firstName,
            last_name = lastName,
            email = email,
            address = address,
            city = city,
            expiration_date = expirationDate
        )

        addProductResult.result = repository.addProduct(addProductRequest)

        return addProductResult
    }
}

