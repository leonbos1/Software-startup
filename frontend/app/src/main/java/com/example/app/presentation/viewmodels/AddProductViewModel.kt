package com.example.app.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.common.UiEvents
import com.example.app.data.remote.request.AddProductRequest
import com.example.app.data.repository.ProductRepository
import com.example.app.domain.results.AddProductResult
import com.example.app.domain.use_cases.AddProductUseCase
import com.example.app.presentation.states.ProdState
import com.example.app.presentation.states.TextFieldState
import com.example.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel@Inject constructor(
    private val addProductUseCase: AddProductUseCase,
): ViewModel() {

    private var _addState = mutableStateOf(ProdState())
    val addState: State<ProdState> = _addState
    private var _nameState = mutableStateOf(TextFieldState())
    val nameState: State<TextFieldState> = _nameState
    private var _descriptionState = mutableStateOf(TextFieldState())
    val descriptionState: State<TextFieldState> = _descriptionState
    private var _phoneNumberState = mutableStateOf(TextFieldState())
    val phoneNumberState: State<TextFieldState> = _phoneNumberState
    private var _lastNameState = mutableStateOf(TextFieldState())
    val lastNameState: State<TextFieldState> = _lastNameState
    private var _firstNameState = mutableStateOf(TextFieldState())
    val firstNameState: State<TextFieldState> = _firstNameState
    private var _emailState = mutableStateOf(TextFieldState())
    val emailNameState: State<TextFieldState> = _emailState
    private var _addressState = mutableStateOf(TextFieldState())
    val addressState: State<TextFieldState> = _addressState
    private var _cityState = mutableStateOf(TextFieldState())
    val cityState: State<TextFieldState> = _cityState
    private var _expirationState = mutableStateOf(TextFieldState())
    val expirationState: State<TextFieldState> = _expirationState

    fun setName(value: String) {
        _nameState.value = nameState.value.copy(text = value)
    }

    fun setDescription(value: String) {
        _descriptionState.value = descriptionState.value.copy(text = value)
    }

    fun setPhoneNumber(value: String) {
        _phoneNumberState.value = phoneNumberState.value.copy(text = value)
    }

    fun setLastName(value: String) {
        _lastNameState.value = lastNameState.value.copy(text = value)
    }

    fun setFirstName(value: String) {
        _firstNameState.value = firstNameState.value.copy(text = value)
    }

    fun setEmail(value: String) {
        _emailState.value = emailNameState.value.copy(text = value)
    }

    fun setAddress(value: String) {
        _addressState.value = addressState.value.copy(text = value)
    }

    fun setCity(value: String) {
        _cityState.value = cityState.value.copy(text = value)
    }

    fun setExpiration(value: String) {
        _expirationState.value = expirationState.value.copy(text = value)
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun addProduct() {
        viewModelScope.launch {
            _addState.value = addState.value.copy(isLoading = false)

            val addProductRequest = addProductUseCase(
                name = nameState.value.text,
                description = descriptionState.value.text,
                phoneNumber = phoneNumberState.value.text,
                firstName = firstNameState.value.text,
                lastName = lastNameState.value.text,
                email = emailNameState.value.text,
                address = addressState.value.text,
                city = cityState.value.text,
                expirationDate = expirationState.value.text
            )

            _addState.value = addState.value.copy(isLoading = false)

            if (addProductRequest.nameError != null) {
                _nameState.value = nameState.value.copy(error = addProductRequest.nameError)
            }
            if (addProductRequest.descriptionError != null) {
                _descriptionState.value =
                    descriptionState.value.copy(error = addProductRequest.descriptionError)
            }
            if (addProductRequest.phoneNumberError != null) {
                _phoneNumberState.value =
                    phoneNumberState.value.copy(error = addProductRequest.phoneNumberError)
            }
            if (addProductRequest.firsNameError != null) {
                _firstNameState.value =
                    firstNameState.value.copy(error = addProductRequest.firsNameError)
            }
            if (addProductRequest.lastNameError != null) {
                _lastNameState.value =
                    lastNameState.value.copy(error = addProductRequest.lastNameError)
            }
            if (addProductRequest.emailError != null) {
                _emailState.value = emailNameState.value.copy(error = addProductRequest.emailError)
            }
            if (addProductRequest.addressError != null) {
                _addressState.value =
                    addressState.value.copy(error = addProductRequest.addressError)
            }
            if (addProductRequest.cityError != null) {
                _cityState.value = cityState.value.copy(error = addProductRequest.cityError)
            }
            if (addProductRequest.expirationDateError != null) {
                _expirationState.value =
                    expirationState.value.copy(error = addProductRequest.expirationDateError)
            }


            when (addProductRequest.result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Success")
                    )
                    _eventFlow.emit(
                        UiEvents.NavigateToProductOverview
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Failed")
                    )
                }

                else -> { }

            }
        }
    }
}
