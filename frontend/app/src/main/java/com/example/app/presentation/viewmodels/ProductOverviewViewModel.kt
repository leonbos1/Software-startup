package com.example.app.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.AuthToken
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.repository.ProductRepository;
import com.example.app.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductOverviewViewModel(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _product = MutableStateFlow<List<ProductResponse>>(emptyList())
    val product = _product.asStateFlow()

    private val _showErrorToastChannel = Channel<String>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _showErrorToastChannel.send("An unknown error occurred")
                    }
                    is Resource.Success -> {
                        result.data?.let { products ->
                            _product.update { products }
                        }
                    }
                    is Resource.Loading -> TODO()
                }
            }
        }
    }

    fun isLoggedIn(): Boolean {
        val authToken = AuthToken.getInstance()
        return authToken.isLoggedIn()
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            val result = productRepository.deleteProduct(productId)
            when (result) {
                is Resource.Success -> {
                    loadProducts()
                }
                is Resource.Error -> {
                    // Send the specific error message to the view
                    _showErrorToastChannel.send(result.message ?: "An unknown error occurred")
                }
                is Resource.Loading -> TODO()
            }
        }
    }


    fun loadProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts().collectLatest { result ->
                result.data?.let { products ->
                    _product.update { products }
                }
            }
        }
   }
}

