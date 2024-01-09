package com.example.app.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.remote.response.ProductResponse
import com.example.app.data.repository.ProductOverviewRepository;
import com.example.app.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductOverviewViewModel(
    private val productOverviewRepository: ProductOverviewRepository
): ViewModel() {

    private val _product = MutableStateFlow<List<ProductResponse>>(emptyList())
    val product = _product.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            productOverviewRepository.getAllProducts().collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _showErrorToastChannel.send(true)
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

}