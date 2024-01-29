package com.example.app.presentation.views.screens

import android.content.Context
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.common.Screens
import com.example.app.data.remote.response.SafeUserResponse
import com.example.app.data.remote.response.UserResponse
import com.example.app.presentation.viewmodels.AccountViewModel
import com.example.app.ui.forms.TextFieldComponent
import com.example.app.util.Resource


@Composable
fun AccountScreen(
    navController: NavController,
    accountViewModel: AccountViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(key1 = accountViewModel.showErrorToastChannel) {
        let {
            accountViewModel.fetchSaveUser()
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
                    Spacer(modifier = Modifier.height(90.dp))

                    TableLayout2(productDetails = productDetails, context = context, viewModel = accountViewModel, navController = navController)

                }
            }

            is Resource.Error -> {
                Column(
                    Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Icon(painter = painterResource(id = R.drawable.user_filled_icon), null)
                    Text("Not logged in", Modifier.padding(vertical = 1.dp))

                    Button(
                        onClick = { navController.navigate(Screens.LoginScreen.route) },
                        colors = ButtonDefaults.buttonColors(Color(0xFFA0C334)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign in", Modifier.padding(vertical = 1.dp))
                    }
                    Divider(
                        color = Color.Black.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 48.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Dont have an account?", color = Color.Black)
                        TextButton(onClick = { navController.navigate(Screens.RegisterScreen.route) }) {
                            Text("Sign Up")
                        }
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
fun TableLayout2(productDetails: UserResponse, context: Context, viewModel: AccountViewModel, navController: NavController) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .height(365.dp)
            .padding(30.dp)

            .shadow(20.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFF7F7F7),
                shape = RoundedCornerShape(5.dp)
            )
            .background(Color(0xFFF7F7F7))
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("First name:       ", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(productDetails.first_name, fontWeight = FontWeight.Medium, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Last name:        ", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(productDetails.last_name, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Username:        ", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(productDetails.username, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Email:               ", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(productDetails.email, fontWeight = FontWeight.Light, fontSize = 21.sp,  )
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Address:           ", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text("${productDetails.address} ${productDetails.address_number}${productDetails.address_number_addition}" , fontWeight = FontWeight.Light, fontSize = 21.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("City:                  ", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(productDetails.city, fontWeight = FontWeight.Light, fontSize = 21.sp)
        }

        Divider(
            color = Color.Black.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 1.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                viewModel.logout()
                navController.navigate(Screens.ProductOverviewScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFA0C334) // Your desired color
                )
            ) {
                Text("Logout")
            }
        }

    }
}



