package com.example.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.repository.ProductRepository
import com.example.app.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _productDetails = MutableStateFlow<Resource<ProductResponse>?>(null)
    val productDetails = _productDetails.asStateFlow()

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            productRepository.getProductDetails(productId).collectLatest { result ->
                _productDetails.value = result
            }
        }
    }

}