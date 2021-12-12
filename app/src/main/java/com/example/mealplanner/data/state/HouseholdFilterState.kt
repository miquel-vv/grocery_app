package com.example.mealplanner.data.state

import androidx.lifecycle.MutableLiveData
import com.example.mealplanner.Observable
import com.example.mealplanner.Observer
import com.example.mealplanner.data.model.Household

object HouseholdFilterState : Observable {
    override val observers: MutableList<Observer> = mutableListOf()
    val liveSelectedHousehold: MutableLiveData<Household> = MutableLiveData()
    var selectedHousehold: Household?=null
        set(value) {
            field=value
            liveSelectedHousehold.value = value
            notifyObservers()
        }
    val liveHouseholds: MutableLiveData<List<Household>> = MutableLiveData()
    var households: List<Household> = listOf()
        set(value) {
            field=value
            liveHouseholds.value = value
            notifyObservers()
        }
}