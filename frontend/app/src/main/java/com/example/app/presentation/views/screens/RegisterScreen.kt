package com.example.app.presentation.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.common.Screens
import com.example.app.presentation.viewmodels.AddProductViewModel
import com.example.app.presentation.viewmodels.RegisterViewModel
import com.example.app.ui.forms.TextFieldComponent

@Composable
fun register(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val firstNameState = registerViewModel.firstNameState.value
    val lastNameState = registerViewModel.lastNameState.value
    val emailState = registerViewModel.emailState.value
    val phoneNumberState = registerViewModel.phoneNumberState.value
    val userNameState = registerViewModel.userNameState.value
    val passwordState = registerViewModel.passwordState.value

    Column(
        Modifier
            .padding(1.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TextFieldComponent(
            state = firstNameState,
            label = "firstName",
            { registerViewModel.setFirstName(it) },
        )
        TextFieldComponent(
            state = lastNameState,
            label = "lastName",
            { registerViewModel.setLastName(it) },
        )
        TextFieldComponent(
            state = emailState,
            label = "email",
            { registerViewModel.setEmail(it) },
        )
        TextFieldComponent(
            state = phoneNumberState,
            label = "phoneNumber",
            { registerViewModel.setPhoneNumber(it) },
        )
        TextFieldComponent(
            state = userNameState,
            label = "userName",
            { registerViewModel.setUserName(it) },
        )
        TextFieldComponent(
            state = passwordState,
            label = "password",

            { registerViewModel.setPassword(it) },
            visualTransformation =  PasswordVisualTransformation(),
        )

        Button(onClick = { registerViewModel.register()}, Modifier.fillMaxWidth()) {
            Text("SIGN up", Modifier.padding(vertical = 1.dp))
        }
        Divider(
            color = Color.Black.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 40.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account?", color = Color.Black)
            TextButton(onClick = {navController.navigate(Screens.LoginScreen.route)}) {
                Text("Sign In")
            }
        }
    }
}
