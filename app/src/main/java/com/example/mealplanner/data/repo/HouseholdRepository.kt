package com.example.mealplanner.data.repo

import android.util.Log
import com.example.mealplanner.Observer
import com.example.mealplanner.data.state.HouseholdFilterState
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.state.UserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

object HouseholdRepository : Observer {

    private val filterState = HouseholdFilterState
    private val userState = UserState
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        Log.d("HouseholdRepo", "HouseholdRepo being initialized")
        userState.addObserver(this)
    }

    override fun update(){
        Log.d("HouseholdRepo", "Updating the households...")
        getHouseholds()
    }

    fun getHouseholds(){
        coroutineScope.launch {
            try {
                val authorization = userState.getAuthHeader()
                val response = MealPlannerApi.householdService.getHouseholds(userState.loggedInUser!!.id, authorization)
                if(response.isSuccessful){
                    filterState.households = response.body()!!.content.map { m -> m.household }
                } else {
                    throw Exception(response.message())
                }
            } catch (e:Exception) {
                //TODO: Handle error.
                throw e
            }
        }
    }

}