package com.example.mealplanner.ui.landing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mealplanner.databinding.ActivityLandingBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}