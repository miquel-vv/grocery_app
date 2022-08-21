package com.example.mealplanner.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.data.repo.LoginRepository
import kotlinx.coroutines.launch

class RegisterViewModel(val loginRepo:LoginRepository) : ViewModel() {

    val registerStatus:LiveData<LoadingStatus>
        get() = loginRepo.registerStatus

    fun registerUser(email:String, username:String, password:String, confirmedPassword:String):Boolean{

        if(password != confirmedPassword){
            return false
        }

        viewModelScope.launch {
            loginRepo.register(email, username, "", password)
        }

        return true
    }
}