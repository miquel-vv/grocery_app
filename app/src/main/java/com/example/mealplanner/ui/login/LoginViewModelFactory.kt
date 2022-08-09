package com.example.mealplanner.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.LoginDataSource
import com.example.mealplanner.data.LoginRepository
import java.lang.IllegalArgumentException

class LoginViewModelFactory(private val prefs: SharedPreferences) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            val dataSource = LoginDataSource(prefs)
            val repo = LoginRepository
            repo.init(dataSource)
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}