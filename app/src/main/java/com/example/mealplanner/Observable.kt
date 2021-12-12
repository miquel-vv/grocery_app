package com.example.mealplanner

interface Observable {
    val observers : MutableList<Observer>

    fun addObserver(o:Observer){
        observers.add(o)
    }

    fun removeObserver(o:Observer){
        observers.remove(o)
    }

    fun notifyObservers(){
        observers.forEach { o -> o.update() }
    }
}