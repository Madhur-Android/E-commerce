package com.example.e_commerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Ecommerce_LIGHT)
        setContentView(R.layout.activity_splash_activitty)

        val gifImageView: ImageView = findViewById(R.id.gifTextView2)

        Handler().postDelayed({
            // Create an Intent that will start the main activity
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish() // Close the splash activity to prevent going back
        }, SPLASH_DISPLAY_LENGTH.toLong())

        Glide.with(this)
            .asGif()
            .load(R.drawable.loading_gif)
            .into(gifImageView)
    }
}