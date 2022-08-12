package com.example.mealplanner.ui.households.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.HouseholdRepository
import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Membership

private const val TAG = "BrowseHouseholdsViewModel"

class BrowseHouseholdsViewModel : ViewModel() {

    private val repo = HouseholdRepository

    val households : LiveData<List<Membership>>
        get() = repo.households

    fun load(){
        repo.fetchHouseholds()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "Clearing householdviewmodel")
    }
}