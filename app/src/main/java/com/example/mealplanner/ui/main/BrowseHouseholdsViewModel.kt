package com.example.mealplanner.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.model.Household

private const val TAG = "BrowseHouseholdsViewModel"

class BrowseHouseholdsViewModel : ViewModel() {

    private val _households = MutableLiveData<List<Household>>()
    val households : LiveData<List<Household>>
        get() = _households

    init{
        _households.value = listOf(
            Household(name = "household1"),
            Household(name = "household2"),
            Household(name = "household3")
        )
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "Clearing householdviewmodel")
    }
}