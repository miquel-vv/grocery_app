package com.example.mealplanner.ui.grocerylist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GroceryListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroceryListViewModel::class.java)){
            return GroceryListViewModel() as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}