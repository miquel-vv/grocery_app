package com.example.mealplanner.data.model

data class LoginBody(var email:String, var password:String)
data class RegisterBody(var email:String, var firstName:String, var lastName:String, var password: String)
data class CreateHousehold(var name:String)
