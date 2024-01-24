package com.example.app.presentation.views.screens

import java.util.Calendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.common.UiEvents
import com.example.app.presentation.viewmodels.AddProductViewModel
import com.example.app.ui.forms.TextFieldComponent

@Composable
fun AddProductScreen(
    navController: NavController,
    addProductViewModel: AddProductViewModel= hiltViewModel()
) {

    val nameState = addProductViewModel.nameState.value
    val descriptionState = addProductViewModel.descriptionState.value
    val phoneNumberState = addProductViewModel.phoneNumberState.value
    val firstNameState = addProductViewModel.firstNameState.value
    val lastNameState = addProductViewModel.lastNameState.value
    val emailState = addProductViewModel.emailNameState.value
    val addressState = addProductViewModel.addressState.value
    val cityState = addProductViewModel.cityState.value
    val expirationDateState = addProductViewModel.expirationState.value

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        addProductViewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvents.NavigateToProductOverview -> {
                    navController.navigate("productOverviewScreen")
                }
            }
        }
    }

    Column(
        Modifier
            .padding(5.dp)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .height(35.dp)
        ) {
            Text(
                text = "Add product",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            TextFieldComponent(
                state = nameState,
                label = "name",
                { addProductViewModel.setName(it) },
            )

            TextFieldComponent(
                state = descriptionState,
                label = "Product description",
                { addProductViewModel.setDescription(it) },
            )

            ExpirationDateFormItem(
                expirationDate = expirationDateState.text,
                errorState = expirationDateState.error
            ) { selectedDate ->
                addProductViewModel.setExpiration(selectedDate)
            }

            Spacer(modifier = Modifier.height(5.dp))

            TextFieldComponent(
                state = firstNameState,
                label = "First name",
                { addProductViewModel.setFirstName(it) },
            )

            TextFieldComponent(
                state = lastNameState,
                label = "Last name",
                { addProductViewModel.setLastName(it) },
            )

            TextFieldComponent(
                state = emailState,
                label = "Email",
                { addProductViewModel.setEmail(it) },
            )

            TextFieldComponent(
                state = addressState,
                label = "Address",
                { addProductViewModel.setAddress(it) },
            )

            TextFieldComponent(
                state = phoneNumberState,
                label = "Phone Number",
                { addProductViewModel.setPhoneNumber(it) },
            )

            TextFieldComponent(
                state = cityState,
                label = "City",
                { addProductViewModel.setCity(it) },
            )

            Button(
                onClick = {
                    addProductViewModel.addProduct()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add product")
            }
        }
    }
}

@Composable
fun ExpirationDateFormItem(expirationDate: String, errorState: String?, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val focusRequester = FocusRequester()

    val labelColor = if (errorState?.isNotEmpty() == true) {
        androidx.compose.material.MaterialTheme.colors.error // Change color to error color if there's an error
    } else {
        androidx.compose.material.MaterialTheme.typography.body1.color // Default color
    }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, selectedYear, monthOfYear, dayOfMonth ->
                val formattedMonth = (monthOfYear + 1).toString().padStart(2, '0')
                val formattedDay = dayOfMonth.toString().padStart(2, '0')
                val selectedDate = "$selectedYear-$formattedMonth-$formattedDay"
                onDateSelected(selectedDate)
            }, year, month, day
        )

        datePickerDialog.show()
    }

    Column {
        TextField(
            value = expirationDate,
            onValueChange = { },
            label = { Text("Expiration Date", color = labelColor) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showDatePicker()
                    }
                }
        )

        // Display error text if errorState is not empty
        if (errorState != "") {
            if (errorState != null) {
                androidx.compose.material.Text(
                    text = errorState,
                    style = androidx.compose.material.MaterialTheme.typography.body2,
                    color = androidx.compose.material.MaterialTheme.colors.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

