package com.example.mealplanner.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mealplanner.R
import com.example.mealplanner.data.LoginDataSource
import com.example.mealplanner.data.repo.HouseholdRepository
import com.example.mealplanner.data.repo.LoginRepository
import com.example.mealplanner.data.repo.MealsRepository
import com.example.mealplanner.data.repo.SchedulesRepository
import com.example.mealplanner.databinding.ActivityMainBinding
import com.example.mealplanner.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userRepo: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar!!
        actionBar.title ="Meal Planner"
        userRepo = LoginRepository(LoginDataSource(this))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                userRepo.logout()
                goToLoginActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToLoginActivity(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent);
    }
}