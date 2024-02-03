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
        expirationDate: String
    ): AddProductResult {
        val nameError = if (name.isBlank()) "Name cannot be blank" else null
        val descriptionError = if (description.isBlank()) "Description cannot be blank" else null
        val expirationDateError = if (expirationDate.isBlank()) "Address cannot be blank" else null

        val addProductResult = AddProductResult()

        if (listOf(nameError, descriptionError, expirationDateError).any { it != null }) {
            addProductResult.apply {
                this.nameError = nameError
                this.descriptionError = descriptionError
                this.expirationDateError = expirationDateError
            }
                return addProductResult
        }

        val addProductRequest = AddProductRequest(
            name = name,
            description = description,
            expiration_date = expirationDate
        )

        addProductResult.result = repository.addProduct(addProductRequest)

        return addProductResult
    }
}

