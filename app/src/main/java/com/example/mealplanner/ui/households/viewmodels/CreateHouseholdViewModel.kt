package com.example.mealplanner.ui.households.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.repo.HouseholdRepository
import com.example.mealplanner.data.LoadingStatus

class CreateHouseholdViewModel : ViewModel() {

    private val householdRepo = HouseholdRepository

    val createHouseholdStatus:LiveData<LoadingStatus>
        get() = householdRepo.createHouseholdStatus

    fun createHousehold(name:String){
        householdRepo.createHousehold(name)
    }

    fun resetStatus(){
        householdRepo.resetCreateStatus()
    }
}