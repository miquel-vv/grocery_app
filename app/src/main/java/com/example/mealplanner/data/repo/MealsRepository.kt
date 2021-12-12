package com.example.mealplanner.data.repo

import android.util.Log
import com.example.mealplanner.Observer
import com.example.mealplanner.data.MealPlannerApi
import com.example.mealplanner.data.model.Meal
import com.example.mealplanner.data.state.DateFilterState
import com.example.mealplanner.data.state.MealState
import com.example.mealplanner.data.state.ScheduleState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


object MealsRepository : Observer{
    private var userRepo = LoginRepository
    private val scheduleState = ScheduleState
    private val mealState = MealState
    private val dateFilter = DateFilterState
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        scheduleState.addObserver(this)
        dateFilter.addObserver(this)
    }

    override fun update() {
        Log.d("MEAL_REPO", "Updating meals..")
        getMeals()
    }

    private fun getMeals(){
        coroutineScope.launch {
            val meals = mutableListOf<Meal>()
            try{
                val authorization = userRepo.getAuthHeader()
                for(schedule in scheduleState.schedules){
                    val response = MealPlannerApi.scheduleService.getMeals(schedule.id, authorization)
                    if(response.isSuccessful){
                        meals.addAll(response.body()!!.content)
                    } else {
                        throw Exception("Getting meals has returned response code: " + response.code().toString())
                    }
                }
                mealState.meals = filterMealsByDate(meals)
            } catch (e:Exception){
                throw e
            }
        }
    }

    private fun filterMealsByDate(meals:List<Meal>):List<Meal>{
        Log.d("USER_REPO","Filtering on date: " + dateFilter.startDateTime.toString())
        return meals.filter { meal ->
            (dateFilter.startDateTime==null || meal.date >= dateFilter.startDateTime)
                    && (dateFilter.endDateTime == null || meal.date <= dateFilter.endDateTime)
        }
    }
}