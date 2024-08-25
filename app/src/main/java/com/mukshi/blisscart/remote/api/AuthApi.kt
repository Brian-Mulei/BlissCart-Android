package com.mukshi.blisscart.remote.api

import com.mukshi.blisscart.data.model.LoginRequest
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.remote.response.HttpGetResponse
 import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.mukshi.blisscart.remote.response.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface  ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/product")
        suspend fun getAllProducts(
        @Query("page") page: Int,
        @Query("size") size: Int = 10
    ):  HttpGetResponse
}