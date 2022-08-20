package com.example.mealplanner.ui.grocerylist.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.dao.GroceryItemDao
import java.lang.IllegalArgumentException

class GroceryListViewModelFactory(private val dataSource: GroceryItemDao, private val application: Application)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroceryListViewModel::class.java)){
            return GroceryListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}