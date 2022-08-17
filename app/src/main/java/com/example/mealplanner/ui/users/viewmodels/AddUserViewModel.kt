package com.example.mealplanner.ui.users.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.HouseholdRepository
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.data.UsersRepository
import com.example.mealplanner.data.model.User

class AddUserViewModel(val position: Int) : ViewModel() {

    private val usersRepo = UsersRepository()
    private val householdRepo = HouseholdRepository

    val userId : LiveData<Int>
        get() = usersRepo.userId

    val addingMemberStatus : LiveData<LoadingStatus>
        get() = householdRepo.memberAdded

    fun searchByEmail(email:String){
        usersRepo.searchUserId(email)
    }

    fun addUserToHousehold(userId:Int){
        var householdId = householdRepo.households.value?.get(position)!!.household.id
        householdRepo.addMember(userId, householdId)
    }
}