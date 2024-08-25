package com.mukshi.blisscart.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.remote.response.HttpGetResponse
 import com.mukshi.blisscart.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _httpResponse = MutableLiveData<HttpGetResponse?>()
    val httpResponse: LiveData<HttpGetResponse?> = _httpResponse

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products


    private var currentPage = 0
    private var totalProducts = 0
    private val pageSize = 10
    private var isLoading = false

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    val currentList = _products.value.orEmpty().toMutableList()

       fun getAll(isInitialLoad: Boolean = false) {

         if (isInitialLoad) {
             currentPage = 0
             _products.value = emptyList()
         }
         if (isLoading) return

         isLoading = true

         viewModelScope.launch {
             try {
                 if(repository.lastPageReached){
                    // _error.value = "No more data to fetch"

                 }
                 else{
                     val newProducts = repository.fetchProducts()
                     currentList.addAll(newProducts)
                     _products.value = currentList

                 }

             } catch (e: Exception) {
                 _error.value = "Error fetching products: ${e.message}"
             } finally {
                 isLoading = false
             }
     }}

}