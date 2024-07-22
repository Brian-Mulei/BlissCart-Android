package com.mukshi.blisscart.ui.view

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mukshi.blisscart.R
import com.mukshi.blisscart.utils.SharedPref

class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPrefsHelper: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPrefsHelper = SharedPref(this)

        val tvWelcome: TextView = findViewById(R.id.textView)
        val username = sharedPrefsHelper.getUsername()
        tvWelcome.text = "Welcome, $username!"

    }
}