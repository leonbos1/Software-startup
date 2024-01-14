package com.example.app.domain.results

import com.example.app.util.Resource

class RegisterResult (
    var firstNameError: String? = null,
    var lastNameError: String? = null,
    var emailError: String? = null,
    var phoneNumberError: String? = null,
    var userNameError: String? = null,
    var passwordError: String? = null,
    var result: Resource<Unit>? = null
)
