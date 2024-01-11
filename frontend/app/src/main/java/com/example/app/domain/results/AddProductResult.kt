package com.example.app.domain.results

import com.example.app.util.Resource

data class AddProductResult(
    var nameError: String? = null,
    var descriptionError: String? = null,
    var phoneNumberError: String? = null,
    var firsNameError: String? = null,
    var lastNameError: String? = null,
    var emailError: String? = null,
    var addressError: String? = null,
    var cityError: String? = null,
    var expirationDateError: String? = null,
    var result: Resource<Unit>? = null
)

