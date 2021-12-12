package com.example.mealplanner.data.state

import com.example.mealplanner.Observable
import com.example.mealplanner.Observer
import com.example.mealplanner.data.model.Schedule

object ScheduleState: Observable {

    var schedules: List<Schedule> = listOf()
        set(value){
            field=value
            notifyObservers()
        }
    override val observers: MutableList<Observer> = mutableListOf()
}