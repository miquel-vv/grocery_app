package com.example.mealplanner.data.model


data class LoginContent(val token: String, val user:User)
data class LoginResponse(val token:String, val content:LoginContent, val succeeded: Boolean)
data class UserResponse(val token: String, val content:User, val succeeded: Boolean)
data class HouseholdsResponse(val token:String, val content:List<Membership>, val succeeded: Boolean)
data class HouseholdResponse(val token: String, val content:Household, val succeeded: Boolean)
data class SchedulesResponse(val token: String, val content:List<Schedule>, val succeeded: Boolean)
data class MealsResponse(val token:String, val content:List<Meal>, val succeeded: Boolean)