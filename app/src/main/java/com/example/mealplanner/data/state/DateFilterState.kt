package com.example.mealplanner.data.state

import com.example.mealplanner.Observable
import com.example.mealplanner.Observer
import java.util.*

object DateFilterState :Observable {

    override val observers: MutableList<Observer> = mutableListOf()

    var startDateTime : Date?=null
        set(value){
            field=value
            notifyObservers()
        }

    var endDateTime: Date?=null
        set(value){
            field = value
            notifyObservers()
        }
}