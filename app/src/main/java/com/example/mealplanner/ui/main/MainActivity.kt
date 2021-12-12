package com.example.mealplanner.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mealplanner.R
import com.example.mealplanner.data.repo.HouseholdRepository
import com.example.mealplanner.data.repo.MealsRepository
import com.example.mealplanner.data.repo.SchedulesRepository
import com.example.mealplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        title="Meal Planner"
    }
}