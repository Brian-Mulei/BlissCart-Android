package com.mukshi.blisscart.repository

import com.mukshi.blisscart.data.model.LoginRequest
import com.mukshi.blisscart.remote.api.ApiService
  import javax.inject.Inject

class AuthRepository  @Inject constructor(
    private val loginApiService: ApiService
) {

    suspend fun login(loginRequest: LoginRequest) = loginApiService.login(loginRequest)
}