package com.example.mealplanner.ui.users.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AddUserViewModelFactory(private val position: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddUserViewModel::class.java)){
            return AddUserViewModel(position) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}