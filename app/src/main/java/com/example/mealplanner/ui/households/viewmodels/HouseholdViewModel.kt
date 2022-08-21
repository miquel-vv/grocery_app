package com.example.mealplanner.ui.households.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.repo.HouseholdRepository
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.data.repo.MemberRepository
import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Member

private const val TAG = "HouseholdViewModel"

class HouseholdViewModel(householdPosition:Int) : ViewModel(){

    private val _household = MutableLiveData<Household>()
    val household: LiveData<Household>
        get() = _household

    val members: LiveData<List<Member>>
        get() = membersRepo.members

    val updateHouseholdStatus: LiveData<LoadingStatus>
        get() = householdRepo.updateHouseholdStatus

    private val householdRepo = HouseholdRepository
    private var membersRepo:MemberRepository

    init {
        val h = getHousehold(householdPosition)
        _household.value = h
        membersRepo = MemberRepository(h)
    }

    fun isOwner():Boolean{
        return membersRepo.isOwner
    }

    fun deleteMember(position: Int){
        membersRepo.removeMember(membersRepo.members.value?.get(position)!!.user.id)
    }

    fun updateHousehold(name:String){
        householdRepo.updateHousehold(_household.value!!.id, name)
    }

    fun resetStatus(){
        householdRepo.resetUpdateStatus()
    }

    fun makeOwner(position: Int){
        membersRepo.makeOwner(members.value!![position].user.id)
    }

    fun revokeOwner(position: Int){
        membersRepo.revokeOwner(members.value!![position].user.id)
    }

    private fun getHousehold(position:Int): Household {
        val households = householdRepo.households.value!!

        return households[position].household
    }
}