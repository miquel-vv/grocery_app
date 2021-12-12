package com.example.mealplanner.data.state

import android.util.Log
import com.example.mealplanner.Observable
import com.example.mealplanner.Observer
import com.example.mealplanner.data.model.User

object UserState : Observable {
    override val observers: MutableList<Observer> = mutableListOf()
    var loggedInUser: User? = null
        set(value){
            field = value
            notifyObservers()
        }
    var token:String = ""

    fun getAuthHeader():String{
        return "Bearer " + this.token
    }
}