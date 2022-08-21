package com.example.mealplanner.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.repo.LoginRepository
import java.lang.IllegalArgumentException

class RegisterViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(LoginRepository) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}