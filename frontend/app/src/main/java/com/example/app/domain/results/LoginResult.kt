package com.example.app.domain.results

import com.example.app.util.Resource

class LoginResult (

    var userNameError: String? = null,
    var passwordError: String? = null,
    var result: Resource<Unit>? = null
)