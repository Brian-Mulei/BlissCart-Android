package com.mukshi.blisscart.repository

import android.util.Log
import com.mukshi.blisscart.data.model.LoginRequest
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.remote.api.ApiService
import javax.inject.Inject

class ProductRepository  @Inject constructor(
    private val productApiService:  ApiService
) {


    private var currentPage = 0
    private val pageSize = 6
      var lastPageReached = false


    suspend fun fetchProducts(): List<Product> {


        val response = productApiService.getAllProducts(currentPage, pageSize)
        currentPage++


        if (response.success && response.data is List<*>) {


            @Suppress("UNCHECKED_CAST")
            lastPageReached =response.isLastPage

            return response.data as List<Product>
        }

        throw Exception("Failed to fetch products: ${response.message}")

    }
}