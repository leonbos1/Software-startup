package com.example.app.presentation.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.repository.ProductOverviewRepositoryImplementation
import com.example.app.presentation.viewmodels.ProductDetailViewModel
import com.example.app.util.Resource
import com.example.app.util.RetrofitInstance

@Composable
fun ProductDetailsScreen(navController: NavController, productId: String?) {
    val context = LocalContext.current
    val productDetailViewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModelFactory())

    LaunchedEffect(productId) {
        productId?.let {
            productDetailViewModel.fetchProductDetails(it)
        }
    }

    val productDetailsState = productDetailViewModel.productDetails.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }

        when (productDetailsState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                productDetailsState.data?.let { productDetails ->
                    Spacer(modifier = Modifier.height(16.dp))

                    TableLayout(productDetails = productDetails)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFFFA500) // Orange color
                            )
                        ) {
                            Text("Whatsapp bieder!")
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text("Error fetching product details", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun TableLayout(productDetails: ProductResponse) {
    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            Text("product naam: ", fontWeight = FontWeight.Bold)
            Text(productDetails.name)
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("product tht datum: ", fontWeight = FontWeight.Bold)
            Text(productDetails.expiration_date)
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("telefoon nummer klant: ", fontWeight = FontWeight.Bold)
            Text(productDetails.phone_number)
        }

    }
}

class ProductDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(ProductOverviewRepositoryImplementation(RetrofitInstance.backendApi)) as T
    }
}