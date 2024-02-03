package com.example.app.presentation.views.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.common.Screens
import com.example.app.data.remote.response.SafeUserResponse
import com.example.app.data.remote.response.UserResponse
import com.example.app.presentation.viewmodels.AccountViewModel
import com.example.app.util.Resource


@Composable
fun AccountEditScreen(
    navController: NavController,
    userId : String?,
    accountViewModel: AccountViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(userId) {
        userId.let {
            accountViewModel.fetchFullUser(it.toString())
        }
    }

    val productDetailsState = accountViewModel.userDetails.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        when (productDetailsState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Resource.Success -> {
                productDetailsState.data?.let { productDetails ->
                    Spacer(modifier = Modifier.height(16.dp))

                    TableLayout3(productDetails = productDetails, context = context, viewModel = accountViewModel, navController = navController)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is Resource.Error -> {
                Text(
                    "Not Logged in",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account?", color = Color.Black)
                    TextButton(onClick = {navController.navigate(Screens.LoginScreen.route)}) {
                        Text("Sign In")
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Dont have an account??", color = Color.Black)
                    TextButton(onClick = {navController.navigate(Screens.RegisterScreen.route)}) {
                        Text("Sign In")
                    }
                }
            }

            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun TableLayout3(productDetails: UserResponse, context: Context, viewModel: AccountViewModel, navController: NavController) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .height(500.dp)
            .padding(20.dp)

            .shadow(20.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFF7F7F7),
                shape = RoundedCornerShape(5.dp)
            )
            .background(Color(0xFFF7F7F7))
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("First name:  ", fontWeight = FontWeight.Light, fontSize = 17.sp)
            Text(productDetails.first_name, fontWeight = FontWeight.Bold, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Last name: ", fontWeight = FontWeight.Light, fontSize = 17.sp)
            Text(productDetails.last_name, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("email: ", fontWeight = FontWeight.Light, fontSize = 17.sp)
            Text(productDetails.email, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Phone number: ", fontWeight = FontWeight.Light, fontSize = 17.sp)
            Text(productDetails.last_name, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("username: ", fontWeight = FontWeight.Light, fontSize = 17.sp)
            Text(productDetails.username, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                viewModel.logout()
                navController.navigate(Screens.AccountScreen.route)
            },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFA0C334) // Your desired color
                )
            ) {
                Text("logout")
            }
        }

    }
}



