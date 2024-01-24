package com.example.app.presentation.views.screens

import android.os.Build
import com.example.app.data.repository.ProductRepositoryImplementation
import com.example.app.presentation.viewmodels.ProductOverviewViewModel
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.common.Screens
import com.example.app.data.remote.response.ProductResponse
import com.example.app.util.RetrofitInstance
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductOverviewScreen(navController: NavController) {
    val productOverviewViewModel: ProductOverviewViewModel = viewModel(factory = ProductOverviewModelFactory())
    val productList = productOverviewViewModel.product.collectAsState().value
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var searchQuery by remember { mutableStateOf("") }

    val sortedAndFilteredList = productList
        .sortedBy {
            LocalDate.parse(it.expiration_date, dateFormatter)
        }
        .filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }

    LaunchedEffect(key1 = productOverviewViewModel.showErrorToastChannel) {
        productOverviewViewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    productOverviewViewModel.loadProducts()

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .width(250.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Gray
                )
            )

            IconButton(
                modifier = Modifier
                    .width(200.dp),
                onClick = { navController.navigate(Screens.AddProductScreen.route) }) {
                Icon(
                    painter = painterResource(R.drawable.add_plus_icon),
                    contentDescription = "add button",
                    modifier = Modifier.size(35.dp)
                )
            }

        }
        if (sortedAndFilteredList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(sortedAndFilteredList.size) { index ->
                    ProductItem(navController, sortedAndFilteredList[index], productOverviewViewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProductItem(navController: NavController, productItem: ProductResponse, productOverviewViewModel: ProductOverviewViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ConfirmDeleteDialog(
            onConfirm = {
                // Handle the deletion here
                productOverviewViewModel.deleteProduct(productItem.id)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

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
        CardDetails(navController, productItem, productOverviewViewModel, { showDialog = true })
    }
}

@Composable
fun CardDetails(navController: NavController, productItem: ProductResponse, productOverviewViewModel: ProductOverviewViewModel, onDeleteClick: () -> Unit) {
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
                text = "BB date: ${productItem.expiration_date}",
                fontSize = 20.sp,)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(R.drawable.remove_icon),
                    contentDescription = "remove button"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { navController.navigate(Screens.ProductOverviewScreen.withArgs(productItem.id)) },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFA0C334) // Orange color
                )
            ) {
                Text("More info")
            }
        }
    }
}

@Composable
fun ConfirmDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Confirm Deletion")
        },
        text = {
            Text("Are you sure you want to delete this product?")
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}

class ProductOverviewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductOverviewViewModel(ProductRepositoryImplementation(RetrofitInstance.backendApi)) as T
    }
}