package com.mukshi.blisscart.remote.response

import com.mukshi.blisscart.data.model.Product

data class HttpGetResponse(val success:Boolean,
                        val data: List<Product>?,
                        val message: String, val isLastPage :Boolean)
