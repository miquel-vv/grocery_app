package com.example.mealplanner.ui.households.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HouseholdViewModelFactory(private val household:Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HouseholdViewModel::class.java)){
            return HouseholdViewModel(household) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}