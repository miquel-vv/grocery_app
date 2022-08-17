package com.example.mealplanner.ui.households.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.HouseholdRepository
import com.example.mealplanner.data.MemberRepository
import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Member
import com.example.mealplanner.data.model.User

private const val TAG = "HouseholdViewModel"

class HouseholdViewModel(householdPosition:Int) : ViewModel(){

    private val _household = MutableLiveData<Household>()
    val household: LiveData<Household>
        get() = _household

    val members: LiveData<List<Member>>
        get() = membersRepo.members

    private val householdRepo = HouseholdRepository
    private var membersRepo:MemberRepository

    init {
        val h = getHousehold(householdPosition)
        _household.value = h
        membersRepo = MemberRepository(h)
    }

    fun deleteMember(position: Int){
        val newList = members.value!!.filterIndexed { index, member ->
            index != position
        }
        //members.value = newList
    }

    private fun getHousehold(position:Int): Household {
        val households = householdRepo.households.value!!

        return households[position].household
    }
}