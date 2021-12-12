package com.example.mealplanner.data.state

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.Observable
import com.example.mealplanner.Observer
import com.example.mealplanner.data.model.Meal
import java.util.Date

object MealState : Observable {
    override val observers: MutableList<Observer> = mutableListOf()
    val liveMeals = MutableLiveData<List<Meal>>()

    var meals:List<Meal> = listOf()
        set(value){
            field= value
            liveMeals.value = meals
            notifyObservers()
        }

}