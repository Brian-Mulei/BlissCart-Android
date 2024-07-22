package com.mukshi.blisscart.remote.response

data class LoginResponse( val access_token: String,
                          val refresh_token: String,
                          val message: String)
