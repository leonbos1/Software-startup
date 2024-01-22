package com.example.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.response.LogoutResponse
import com.example.app.data.remote.response.SafeUserResponse
import com.example.app.data.remote.response.UserResponse
import com.example.app.data.repository.UserRepository
import com.example.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _userLogout = MutableStateFlow<Resource<LogoutResponse>?>(null)
    val userLogout = _userLogout.asStateFlow()

    private val _userDetails = MutableStateFlow<Resource<UserResponse>?>(null)
    val userDetails = _userDetails.asStateFlow()

    private val _productDetails = MutableStateFlow<Resource<SafeUserResponse>?>(null)
    val productDetails = _productDetails.asStateFlow()
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun fetchSaveUser() {
        viewModelScope.launch {
            userRepository.getCurUser().collectLatest { result ->
                _productDetails.value = result
            }
        }
    }
    fun fetchFullUser(userId: String) {
        viewModelScope.launch {
            userRepository.getUserDetails(userId).collectLatest { result ->
                _userDetails.value = result
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            userRepository.logout().collectLatest { result ->
                _userLogout.value = result
            }
        }
    }
}