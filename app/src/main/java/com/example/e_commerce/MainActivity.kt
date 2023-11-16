package com.example.e_commerce

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.e_commerce.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener(navListener)

        // Set the initial fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commit()

//        val switchTheme: Switch = findViewById(R.id.switchTheme)
//
//        // Set initial state based on current mode
//        switchTheme.isChecked = isNightModeEnabled()
//
//        switchTheme.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // Enable dark mode
//                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                // Enable light mode
//                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//        }
    }
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment: Fragment = when (item.itemId) {
            R.id.homeFragment -> HomeFragment()
            R.id.shopFragment -> ShopFragment()
            R.id.bagFragment -> BagFragment()
            R.id.favoritesFragment -> FavoritesFragment()
            R.id.profileFragment -> ProfileFragment()
            else -> throw IllegalArgumentException("Unknown navigation item clicked")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, selectedFragment)
            .commit()

        true
    }
//    private fun setNightMode(mode: Int) {
//        AppCompatDelegate.setDefaultNightMode(mode)
//        recreate() // Recreate the activity to apply the new theme
//    }
//
//    private fun isNightModeEnabled(): Boolean {
//        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
//    }
}