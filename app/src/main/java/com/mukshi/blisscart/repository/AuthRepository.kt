package com.mukshi.blisscart.repository

import com.mukshi.blisscart.remote.api.LoginApiService
import com.mukshi.blisscart.remote.api.LoginRequest
import javax.inject.Inject

class AuthRepository  @Inject constructor(
    private val loginApiService: LoginApiService
) {

    suspend fun login(loginRequest: LoginRequest) = loginApiService.login(loginRequest)
}