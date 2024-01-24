package com.example.app.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.common.UiEvents
import com.example.app.domain.use_cases.RegisterUseCase
import com.example.app.presentation.states.ProdState
import com.example.app.presentation.states.TextFieldState
import com.example.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(
    private val registerUseCase: RegisterUseCase,
): ViewModel() {

    private var _addState = mutableStateOf(ProdState())
    val addState: State<ProdState> = _addState
    private var _firstNameState = mutableStateOf(TextFieldState())
    val firstNameState: State<TextFieldState> = _firstNameState
    private var _lastNameState = mutableStateOf(TextFieldState())
    val lastNameState: State<TextFieldState> = _lastNameState
    private var _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState
    private var _phoneNumberState = mutableStateOf(TextFieldState())
    val phoneNumberState: State<TextFieldState> = _phoneNumberState
    private var _userNameState = mutableStateOf(TextFieldState())
    val userNameState: State<TextFieldState> = _userNameState
    private var _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState
    private var _addressState = mutableStateOf(TextFieldState())
    val addressState: State<TextFieldState> = _addressState
    private var _addressNumberState = mutableStateOf(TextFieldState())
    val addresNumberState: State<TextFieldState> = _addressNumberState
    private var _cityState = mutableStateOf(TextFieldState())
    val cityState: State<TextFieldState> = _cityState
    private var _postalCodeState = mutableStateOf(TextFieldState())
    val postalCodeState: State<TextFieldState> = _postalCodeState

    fun setFirstName(value:String){ _firstNameState.value = firstNameState.value.copy(text = value) }
    fun setLastName(value:String){ _lastNameState.value = lastNameState.value.copy(text = value) }
    fun setEmail(value:String){ _emailState.value = emailState.value.copy(text = value) }
    fun setPhoneNumber(value:String){ _phoneNumberState.value = phoneNumberState.value.copy(text = value) }
    fun setUserName(value:String){ _userNameState.value = userNameState.value.copy(text = value) }
    fun setPassword(value:String){ _passwordState.value = passwordState.value.copy(text = value) }
    fun setAddress(value:String){ _addressState.value = addressState.value.copy(text = value) }
    fun setAddressNumber(value:String){ _addressNumberState.value = addresNumberState.value.copy(text = value) }
    fun setCity(value:String){ _cityState.value = cityState.value.copy(text = value) }
    fun setPostalCode(value:String){ _postalCodeState.value = postalCodeState.value.copy(text = value) }

     val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun register() {
        viewModelScope.launch {
            _addState.value = addState.value.copy(isLoading = false)

            val registerRequest = registerUseCase(
                firstName = firstNameState.value.text,
                lastName = lastNameState.value.text,
                email = emailState.value.text,
                phoneNumber = phoneNumberState.value.text,
                userName = userNameState.value.text,
                password = passwordState.value.text,
                address = addressState.value.text,
                addressNumber = addresNumberState.value.text,
                city = cityState.value.text,
                postalCode = postalCodeState.value.text
            )

            _addState.value = addState.value.copy(isLoading = false)

            if (registerRequest.firstNameError != null){
                _firstNameState.value=firstNameState.value.copy(error = registerRequest.firstNameError)
            }
            if (registerRequest.lastNameError != null){
                _lastNameState.value=lastNameState.value.copy(error = registerRequest.lastNameError)
            }
            if (registerRequest.emailError != null){
                _emailState.value=emailState.value.copy(error = registerRequest.emailError)
            }
            if (registerRequest.phoneNumberError != null){
                _phoneNumberState.value=phoneNumberState.value.copy(error = registerRequest.phoneNumberError)
            }
            if (registerRequest.userNameError != null){
                _userNameState.value=userNameState.value.copy(error = registerRequest.userNameError)
            }
            if (registerRequest.passwordError != null){
                _passwordState.value=passwordState.value.copy(error = registerRequest.passwordError)
            }
            if (registerRequest.addressError != null){
                _addressState.value=addressState.value.copy(error = registerRequest.addressError)
            }
            if (registerRequest.addressError != null){
                _addressNumberState.value=addresNumberState.value.copy(error = registerRequest.addressNumberError)
            }
            if (registerRequest.cityError != null){
                _cityState.value=cityState.value.copy(error = registerRequest.cityError)
            }
            if (registerRequest.postalCodeError != null){
                _postalCodeState.value=postalCodeState.value.copy(error = registerRequest.postalCodeError)
            }


            when(registerRequest.result){
                is Resource.Success->{
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Succes")
                    )
                }
                is Resource.Error->{
                    _userNameState.value=userNameState.value.copy(error = "username already in use")
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Failed")
                    )
                }
                else -> {

                }
            }

        }
    }
}