package com.example.mealplanner.ui.households.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CreateHouseholdViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CreateHouseholdViewModel::class.java)){
            return CreateHouseholdViewModel() as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}