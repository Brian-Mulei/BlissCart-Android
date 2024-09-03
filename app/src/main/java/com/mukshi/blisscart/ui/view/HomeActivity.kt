package com.mukshi.blisscart.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mukshi.blisscart.App
import com.mukshi.blisscart.R
import com.mukshi.blisscart.ui.viewmodel.CartViewModel
import com.mukshi.blisscart.ui.viewmodel.CartViewModelFactory
import com.mukshi.blisscart.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPrefsHelper: SharedPref

    val homefragment: Fragment = HomeFragment()
    val cartFragment: Fragment = CartFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = homefragment

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(App.instance.getDatabase().cartDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        fm.beginTransaction().add(R.id.fragment_container, cartFragment, "2").hide(cartFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,homefragment, "1").commit();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.nav_home -> {

                    fm.beginTransaction().hide(active).show(homefragment).commit();
                    active = homefragment;

                  //  loadFragment(HomeFragment())
                    true
                }
                R.id.nav_saved -> {

                    fm.beginTransaction().hide(active).show(cartFragment).commit();
                    active = cartFragment;

                    //     loadFragment(CartFragment())
                    true
                }

                else->false
            }


        }

        if(savedInstanceState == null){
            bottomNavigationView.selectedItemId = R.id.nav_home
        }
       // sharedPrefsHelper = SharedPref(this)

     //   val tvWelcome: TextView = findViewById(R.id.textView)
       // val username = sharedPrefsHelper.getUsername()
   //     tvWelcome.text = "Welcome, $username!"

    }

    private fun loadFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }
}