package com.example.app.presentation.views.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.repository.ProductRepositoryImplementation
import com.example.app.presentation.viewmodels.ProductDetailViewModel
import com.example.app.presentation.viewmodels.ProductOverviewViewModel
import com.example.app.util.Resource
import com.example.app.util.RetrofitInstance

@Composable
fun ProductDetailsScreen(navController: NavController, productId: String?) {
    val context = LocalContext.current
    val productDetailViewModel: ProductDetailViewModel =
        viewModel(factory = ProductDetailViewModelFactory())

    LaunchedEffect(productId) {
        productId?.let {
            productDetailViewModel.fetchProductDetails(it)
        }
    }

    val productDetailsState = productDetailViewModel.productDetails.collectAsState().value

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

                    TableLayout(productDetails = productDetails, context = context)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is Resource.Error -> {
                Text(
                    "Error fetching product details",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun TableLayout(productDetails: ProductResponse, context: Context) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .height(400.dp)
            .padding(10.dp)

            .shadow(20.dp)
            .border(
                width = 2.dp,
                color = Color(0xFFF7F7F7),
                shape = RoundedCornerShape(5.dp)
            )
            .background(Color(0xFFF7F7F7))
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(productDetails.name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Placed on: ", fontWeight = FontWeight.Light, fontSize = 12.sp)
            Text(productDetails.created, fontWeight = FontWeight.Light, fontSize = 12.sp)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text("Description: ", fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text(productDetails.description)
        }
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)) {
            Text("Product Expiry Date: ", fontWeight = FontWeight.Bold)
            Text(productDetails.expiration_date)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Text("User", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text("UserName: ", fontWeight = FontWeight.Bold)
            Text(productDetails.user.username)
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text("Phone number: ", fontWeight = FontWeight.Bold)
            Text(productDetails.user.phone_number)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                //NEEDS TO BE TESTED
                onClick = {
                    val mobileNumber = productDetails.user.phone_number
                    val message =
                        "Hello, ${productDetails.user.phone_number} I would like to come pick up: ${productDetails.name}! Can we make an appointment?"
                    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://api.whatsapp.com/send?phone=$mobileNumber&text=$message")
                        setPackage("com.whatsapp")
                    }

                    if (appInstalledOrNot(whatsappIntent, context)) {
                        context.startActivity(whatsappIntent)
                    } else {
                        Toast.makeText(
                            context,
                            "WhatsApp not installed on your device",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFA0C334) // Your desired color
                )
            ) {
                Text("Whatsapp supplier!")
            }
        }

    }
}

fun appInstalledOrNot(intent: Intent, context: Context): Boolean {
    val activities = context.packageManager.queryIntentActivities(intent, 0)
    return activities.size > 0
}

class ProductDetailViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(
            ProductRepositoryImplementation(
                RetrofitInstance.backendApi
            )
        ) as T
    }
}
