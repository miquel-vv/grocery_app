package com.example.mealplanner.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.data.LoginRepository

class LoginViewModel: ViewModel(){

    private var email:String = ""
    private var password:String = ""
    private val repo = LoginRepository

    val loadingStatus : LiveData<LoadingStatus>
        get() = repo.loadingStatus

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