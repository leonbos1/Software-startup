package com.example.app.presentation.viewmodels

import android.util.Log
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
    private var _expirationState = mutableStateOf(TextFieldState())
    val expirationState: State<TextFieldState> = _expirationState

    fun setName(value: String) {
        _nameState.value = nameState.value.copy(text = value)
    }

    fun setDescription(value: String) {
        _descriptionState.value = descriptionState.value.copy(text = value)
    }

    fun setExpiration(value: String) {
        _expirationState.value = expirationState.value.copy(text = value)
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    fun addProduct() {
        viewModelScope.launch {
            _addState.value = addState.value.copy(isLoading = false)

            val addProductRequest = addProductUseCase(
                name = nameState.value.text,
                description = descriptionState.value.text,
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
            if (addProductRequest.expirationDateError != null) {
                _expirationState.value =
                    expirationState.value.copy(error = addProductRequest.expirationDateError)
            }


            when (addProductRequest.result) {
                is Resource.Success -> {
                    Log.d("AddProductViewModel", "Product added successfully, navigating to overview")
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
