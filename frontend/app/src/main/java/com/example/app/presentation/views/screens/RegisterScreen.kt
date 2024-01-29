package com.example.app.presentation.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app.common.Screens
import com.example.app.presentation.viewmodels.AddProductViewModel
import com.example.app.presentation.viewmodels.RegisterViewModel
import com.example.app.ui.forms.TextFieldComponent
import kotlinx.coroutines.launch

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
    val addressState = registerViewModel.addressState.value
    val addressNumberState = registerViewModel.addresNumberState.value
    val addressNumberAddState = registerViewModel.addresNumberAddState.value
    val cityState = registerViewModel.cityState.value
    val postalCodeState = registerViewModel.postalCodeState.value

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    fun collectEvent(){
    registerViewModel.viewModelScope.launch {
        registerViewModel._eventFlow.collect { event ->
            if(event.toString().equals("SnackbarEvent(message=Succes)")) {
                navController.navigate(Screens.LoginScreen.route)
            }
            else {

            }
        }
    }
}

    Column(
        Modifier
            .padding(1.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally



    ) {
        TextFieldComponent(
            state = firstNameState,
            label = "First Name",
            { registerViewModel.setFirstName(it) },
        )
        TextFieldComponent(
            state = lastNameState,
            label = "Last Name",
            { registerViewModel.setLastName(it) },
        )
        TextFieldComponent(
            state = emailState,
            label = "Email",
            { registerViewModel.setEmail(it) },
        )
        TextFieldComponent(
            state = phoneNumberState,
            label = "Phone Number",
            { registerViewModel.setPhoneNumber(it) },
        )
        TextFieldComponent(
            state = userNameState,
            label = "Username",
            { registerViewModel.setUserName(it) },
        )
        TextFieldComponent(
            state = addressState,
            label = "Address",
            { registerViewModel.setAddress(it) },
        )
        TextFieldComponent(
            state = addressNumberState,
            label = "Addres Number",
            { registerViewModel.setAddressNumber(it) },
        )
        TextFieldComponent(
            state = addressNumberAddState,
            label = "Addres letter",
            { registerViewModel.setAddressNumberAdd(it) },
        )
        TextFieldComponent(
            state = cityState,
            label = "City",
            { registerViewModel.setCity(it) },
        )
        TextFieldComponent(
            state = postalCodeState,
            label = "Postalcode",
            { registerViewModel.setPostalCode(it) },
        )
        TextFieldComponent(
            state = passwordState,
            label = "Password",

            { registerViewModel.setPassword(it) },
            visualTransformation =  PasswordVisualTransformation(),
        )

        Button(onClick = {
            collectEvent()
            registerViewModel.register()
                         }, Modifier.fillMaxWidth()) {
            Text("Sign up", Modifier.padding(vertical = 1.dp))
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
