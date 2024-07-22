package com.mukshi.blisscart.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.mukshi.blisscart.remote.response.LoginResponse

data class LoginRequest(val username: String, val password: String)

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}