package com.example.app.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.common.UiEvents
import com.example.app.domain.use_cases.LoginUseCase
import com.example.app.presentation.states.ProdState
import com.example.app.presentation.states.TextFieldState
import com.example.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val loginUseCase: LoginUseCase,
): ViewModel() {


    private var _addState = mutableStateOf(ProdState())
    val addState: State<ProdState> = _addState

    private var _userNameState = mutableStateOf(TextFieldState())
    val userNameState: State<TextFieldState> = _userNameState
    private var _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setUserName(value:String){ _userNameState.value = userNameState.value.copy(text = value) }
    fun setPassword(value:String){ _passwordState.value = passwordState.value.copy(text = value) }

    private val  _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun collectEvent(): Boolean{
        var login = false
        viewModelScope.launch {
            eventFlow.collectLatest { event ->
                if(event.toString().equals("SnackbarEvent(message=Succes)")) {
                    login = true
                }
            }

        }
        return login
    }

    fun login() {
        viewModelScope.launch {
            _addState.value = addState.value.copy(isLoading = false)

            val loginRequest = loginUseCase(
                userName = userNameState.value.text,
                password = passwordState.value.text
            )

            _addState.value = addState.value.copy(isLoading = false)



            if (loginRequest.userNameError != null){
                _userNameState.value=userNameState.value.copy(error = loginRequest.userNameError)
            }
            if (loginRequest.passwordError != null){
                _passwordState.value=passwordState.value.copy(error = loginRequest.passwordError)
            }


            when(loginRequest.result){
                is Resource.Success->{
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent("Succes")
                    )
                }
                is Resource.Error->{
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