package com.example.mealplanner.ui.grocerylist.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.data.GroceryListRepo
import com.example.mealplanner.data.HouseholdRepository
import com.example.mealplanner.data.model.GroceryItem
import com.example.mealplanner.data.model.Membership
import kotlinx.coroutines.launch

private const val TAG = "GROCERY_LIST_VIEW_MODEL"

class GroceryListViewModel : ViewModel() {

    private val repo = GroceryListRepo()
    private val householdRepo = HouseholdRepository

    val households : LiveData<List<Membership>>
        get() = householdRepo.households

    private val _groceryList = MutableLiveData(setOf<GroceryItem>())
    val groceryList : LiveData<Set<GroceryItem>>
        get() = _groceryList

    fun getHouseholds(){
        householdRepo.fetchHouseholds()
    }

    fun getGroceryList(memberships:List<Membership>){
        viewModelScope.launch {
            val groceryItems = repo.getGroceryList(memberships.map { m -> m.household })
            _groceryList.value = groceryItems
        }
    }
}