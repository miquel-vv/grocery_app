package com.example.mealplanner.ui.households.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.HouseholdRepository
import com.example.mealplanner.data.model.Household

private const val TAG = "BrowseHouseholdsViewModel"

class BrowseHouseholdsViewModel : ViewModel() {

    private val _households = MutableLiveData<List<Household>>()
    val households : LiveData<List<Household>>
        get() = _households

    private val householdsRepo = HouseholdRepository

    init{
        _households.value = HouseholdRepository.getHouseholds()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "Clearing householdviewmodel")
    }
}