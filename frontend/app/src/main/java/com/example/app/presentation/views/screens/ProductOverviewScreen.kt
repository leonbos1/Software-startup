package com.example.app.presentation.views.screens

import android.content.Context
import android.os.Build
import com.example.app.data.repository.ProductRepositoryImplementation
import com.example.app.presentation.viewmodels.ProductOverviewViewModel
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
    val context = LocalContext.current
    val productOverviewViewModel: ProductOverviewViewModel = viewModel(factory = ProductOverviewModelFactory(context))
    val productList = productOverviewViewModel.product.collectAsState().value
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var searchQuery by remember { mutableStateOf("") }
    var selectedRadius by remember { mutableStateOf("All") }
    val radiusOptions = listOf("5", "10", "20", "50", "100", "All")
    var expanded by remember { mutableStateOf(false) }

    val sortedAndFilteredList = productList
        .sortedBy {
            LocalDate.parse(it.expiration_date, dateFormatter)
        }
        .filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }

    LaunchedEffect(key1 = productOverviewViewModel.showErrorToastChannel) {
        productOverviewViewModel.showErrorToastChannel.collectLatest { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

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
                modifier = Modifier.width(200.dp),
                onClick = {
                    if (productOverviewViewModel.isLoggedIn()) {
                        navController.navigate(Screens.AddProductScreen.route)
                    } else {
                        Toast.makeText(
                            context,
                            "You need to log in first, before adding a product.",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.navigate(Screens.AccountScreen.route)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_plus_icon),
                    contentDescription = "add button",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Radius: ")
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                if (productOverviewViewModel.isLoggedIn()) {
                    expanded = true
                } else {
                    Toast.makeText(context, "You must be logged in to use this feature.", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.AccountScreen.route)
                }
            }) {
                if (selectedRadius == "All") {
                    Text("All")
                } else {
                    Text("$selectedRadius km")
                }
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                radiusOptions.forEach { radius ->
                    DropdownMenuItem(onClick = {
                        selectedRadius = radius
                        expanded = false
                        if (radius == "All") {
                            productOverviewViewModel.loadProducts()
                        } else {
                            productOverviewViewModel.loadProductsInRadius(radius)
                        }
                    }) {
                        if (radius == "All") {
                            Text("All")
                        } else {
                            Text("$radius km")
                        }
                    }
                }
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
fun CardDetails(
    navController: NavController,
    productItem: ProductResponse,
    productOverviewViewModel: ProductOverviewViewModel,
    onDeleteClick: () -> Unit) {

    val context = LocalContext.current

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
                text = "Expiry date: ${productItem.expiration_date}",
                fontSize = 20.sp,)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (productOverviewViewModel.isLoggedIn()) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        painter = painterResource(R.drawable.remove_icon),
                        contentDescription = "remove button"
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (productOverviewViewModel.isLoggedIn()) {
                        navController.navigate(Screens.ProductOverviewScreen.withArgs(productItem.id))
                    } else {
                        Toast.makeText(
                            context,
                            "You need to log in first",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Screens.AccountScreen.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFA0C334)
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

class ProductOverviewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductOverviewViewModel(
            ProductRepositoryImplementation(
                RetrofitInstance.backendApi
            )
        ) as T
    }
}
