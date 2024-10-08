package com.mukshi.blisscart.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mukshi.blisscart.R
import com.mukshi.blisscart.ui.viewmodel.LoginViewModel
import com.mukshi.blisscart.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPrefsHelper: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        sharedPrefsHelper = SharedPref(this)


        if (sharedPrefsHelper.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        val etUsername: EditText = findViewById(R.id.etUsername)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
     //   val tvMessage: TextView = findViewById(R.id.tvMessage)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            loginViewModel.login(username, password)
        }

        loginViewModel.loginResponse.observe(this) { response ->
            response?.let {
           //     tvMessage.text = it.message
             //   tvMessage.visibility = TextView.VISIBLE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                sharedPrefsHelper.saveUsername(etUsername.text.toString().trim())
                sharedPrefsHelper.saveUserId(it.user_id)
                if (it.access_token.isNotEmpty()) {
                    // Navigate to home page

                    sharedPrefsHelper.saveToken(it.access_token)
                    sharedPrefsHelper.setLoggedIn()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        }

        loginViewModel.error.observe(this) { error ->
            error?.let {
             //   tvMessage.text = it
               // tvMessage.visibility = TextView.VISIBLE
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()

            }
        }
    }

}