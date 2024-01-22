package com.example.app.presentation.views.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app.common.Screens
import com.example.app.data.AuthToken
import com.example.app.presentation.viewmodels.LoginViewModel
import com.example.app.ui.forms.TextFieldComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun login(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()

) {

    val context = LocalContext.current
    val userNameState = loginViewModel.userNameState.value
    val passwordState = loginViewModel.passwordState.value

    fun collectEvent(){


        loginViewModel.viewModelScope.launch {
            loginViewModel._eventFlow.collect { event ->
                if(event.toString().equals("SnackbarEvent(message=Succes)")) {
                    navController.navigate(Screens.AccountScreen.route)
                }
                else {

                }
            }
        }
    }
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

        Button(onClick = {
            loginViewModel.login()
            collectEvent()

        }, colors = ButtonDefaults.buttonColors(Color(0xFFA0C334)),
                  modifier =  Modifier.fillMaxWidth()) {
            Text("SIGN in", Modifier.padding(vertical = 1.dp))
        }
        Divider(
            color = Color.Black.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
            )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dont have an account?", color = Color.Black)
            TextButton(onClick = {navController.navigate(Screens.RegisterScreen.route)}) {
                Text("Sign Up")
            }
        }
    }
}

