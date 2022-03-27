package com.example.mealplanner.ui.landing

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.LoginDataSource
import com.example.mealplanner.data.repo.LoginRepository

class LoginViewModel: ViewModel(){

    private var email:String = ""
    private var password:String = ""
    private val repo:LoginRepository = LoginRepository

    init {
        Log.i("LoginViewModel", "LoginViewModel created")
    }

    fun loginUser(){
        repo.login(email, password)
    }

    fun setEmail(email:String){
        this.email = email
    }

    fun setPassword(password:String){
        this.password = password
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "LoginViewModel destroyed")
    }
}