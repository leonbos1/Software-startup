package com.example.app.presentation.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.presentation.viewmodels.LoginViewModel
import com.example.app.ui.forms.TextFieldComponent

@Composable
fun login(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()

) {


    val userNameState = loginViewModel.userNameState.value
    val passwordState = loginViewModel.passwordState.value

    Column(
        Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        TextFieldComponent(
            state = userNameState,
            label = "userName",
            { loginViewModel.setUserName(it) },
        )
        TextFieldComponent(
            state = passwordState,
            label = "password",

            { loginViewModel.setPassword(it) },
            visualTransformation =  PasswordVisualTransformation(),
        )

        Button(onClick = { loginViewModel.login()}, Modifier.fillMaxWidth()) {
            Text("SIGN up", Modifier.padding(vertical = 1.dp))
        }
    }
}
