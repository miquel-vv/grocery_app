package com.example.mealplanner.data.repo

import android.util.Log
import com.example.mealplanner.data.state.HouseholdFilterState
import com.example.mealplanner.Observer
import com.example.mealplanner.data.state.ScheduleState
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.model.Schedule
import com.example.mealplanner.data.model.SchedulesResponse
import com.example.mealplanner.data.state.UserState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

object SchedulesRepository : Observer{

    private var userState = UserState
    private val householdFilterState = HouseholdFilterState
    private val scheduleFilterState = ScheduleState
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        householdFilterState.addObserver(this)
    }

    override fun update(){
        Log.d("SCHEDULE_REPO", "Updating the schedules..")
        getSchedules()
    }

    fun getSchedules(){
        coroutineScope.launch {
            val schedules = mutableListOf<Schedule>()
            var response: Response<SchedulesResponse>
            try{
                val authorization = userState.getAuthHeader()
                val selected = householdFilterState.selectedHousehold
                for(household in householdFilterState.households){
                    if(selected == null || household==selected){
                        response = MealPlannerApi.householdService.getSchedules(household.id, authorization)
                        schedules.addAll(processResponse(response))
                    }
                }
            } catch (e:Exception){
                throw e
            }

            scheduleFilterState.schedules = schedules
        }
    }

    private fun processResponse(response: Response<SchedulesResponse>):List<Schedule>{
        if(response.isSuccessful){
            return response.body()!!.content
        } else {
            throw Exception("Response returned with code: " + response.code().toString())
        }
    }
}