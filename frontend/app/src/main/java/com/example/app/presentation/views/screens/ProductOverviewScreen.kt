package com.example.app.presentation.views.screens

import android.graphics.Color.rgb
import com.example.app.data.repository.ProductOverviewRepositoryImplementation
import com.example.app.presentation.viewmodels.ProductOverviewViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app.common.Screens
import com.example.app.data.remote.response.ProductResponse
import com.example.app.util.RetrofitInstance
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductOverviewScreen(navController: NavController) {
    val viewModel: ProductOverviewViewModel = viewModel(factory = FoodOverviewModelFactory())
    val productList = viewModel.product.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if (productList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productList.size) { index ->
                    ProductItem(navController, productList[index])
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProductItem(navController: NavController, productItem: ProductResponse) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F7F7),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 240.dp)
            .padding(5.dp)
    ) {
        CardDetails(navController, productItem)
    }
}

@Composable
fun CardDetails(navController: NavController, productItem: ProductResponse) {
    Column {
        Column(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 5.dp, top = 5.dp),
        ) {
            Text(
                text = productItem.name,
                fontSize = 25.sp,)
            Text(
                text = productItem.description,
                fontSize = 20.sp,)
            Text(
                text = "Tht datum: ${productItem.expiration_date}",
                fontSize = 20.sp,)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 3.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.navigate(Screens.ProductOverviewScreen.withArgs(productItem.id)) },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFA0C334) // Orange color
                )
            ) {
                Text("Meer details")
            }
        }
    }
}

class FoodOverviewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductOverviewViewModel(ProductOverviewRepositoryImplementation(RetrofitInstance.backendApi)) as T
    }
}