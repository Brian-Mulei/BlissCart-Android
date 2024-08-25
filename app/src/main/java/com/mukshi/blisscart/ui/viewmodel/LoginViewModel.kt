package com.mukshi.blisscart.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukshi.blisscart.data.model.LoginRequest
 import com.mukshi.blisscart.remote.response.LoginResponse
import com.mukshi.blisscart.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    _loginResponse.value = response.body()
                } else {
                    _error.value = response.errorBody()?.string()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}