package com.example.mealplanner.ui.households.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Member
import com.example.mealplanner.data.model.User

private const val TAG = "HouseholdViewModel"

class HouseholdViewModel(householdPosition:Int) : ViewModel(){

    private val _household = MutableLiveData<Household>()
    val household: LiveData<Household>
        get() = _household

    private val _members = MutableLiveData<List<Member>>()
    val members: LiveData<List<Member>>
        get() = _members

    init {
        val h = getHousehold(householdPosition)
        _household.value = h
        _members.value = getMembers(h)
    }

    private fun getHousehold(position:Int): Household {
        val households = listOf(
            Household(name = "household1"),
            Household(name = "household2"),
            Household(name = "household3")
        )

        return households[position]
    }

    private fun getMembers(household: Household):List<Member>{
        val users = listOf(
            User(firstName = "John", lastName = "Doe", email = "john.doe@mail.com"),
            User(firstName = "Samantha", lastName = "Smith", email = "s.smith@mail.com"),
            User(firstName = "Sofie", lastName = "Onderbeke", email = "sofie.onderbeke@mail.com"),
        )
        return listOf(
            Member(isOwner = true, user=users[0]),
            Member(isOwner = false, user=users[1]),
            Member(isOwner = false, user=users[2])
        )
    }
}