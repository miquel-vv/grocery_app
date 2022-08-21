package com.example.mealplanner.ui.grocerylist.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.GroceryListRepository
import com.example.mealplanner.data.HouseholdRepository
import com.example.mealplanner.data.dao.GroceryItemDao
import java.lang.IllegalArgumentException

class GroceryListViewModelFactory(private val dataSource: GroceryItemDao, private val application: Application)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroceryListViewModel::class.java)){
            val repo = GroceryListRepository(dataSource, application.getSharedPreferences("grocery_list", Context.MODE_PRIVATE))
            return GroceryListViewModel(repo, HouseholdRepository, application) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class.")
    }
}