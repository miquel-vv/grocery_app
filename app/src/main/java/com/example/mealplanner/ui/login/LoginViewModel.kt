package com.example.mealplanner.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.LoginRepository
import com.example.mealplanner.ui.login.LoginStatus

class LoginViewModel: ViewModel(){

    private var email:String = ""
    private var password:String = ""
    private val repo = LoginRepository

    val loginStatus : LiveData<LoginStatus>
        get() = repo.loginStatus

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