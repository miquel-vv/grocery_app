package com.example.mealplanner.data

import com.example.mealplanner.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.mealsplanner.be/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService {
    @POST("auth/login")
    fun login(@Body body: LoginBody): Call<LoginResponse>

    @GET("users/{id}")
    fun get(@Path("id") userId:Number, @Header("Authorization") authorization:String):
            Call<UserResponse>
}

interface HouseholdApiService {
    @GET("users/{id}/households")
    fun getHouseholds(@Path("id") userId:Number, @Header("Authorization") authorization:String):
            Call<HouseholdsResponse>

    @GET("households/{id}/schedules")
    fun getSchedules(@Path("id") householdId: Number, @Header("Authorization") authorization:String):
            Call<SchedulesResponse>

    @GET("households/{id}/members")
    fun getMembers(@Path("id") householdId: Number, @Header("Authorization") authorization:String):
            Call<MembersResponse>
}

interface ScheduleApiService {
    @GET("schedules/{id}/meals")
    fun getMeals(@Path("id") scheduleId:Number, @Header("Authorization") authorization:String):
            Call<MealsResponse>
}

object MealPlannerApi {
    val loginService : LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    val householdService: HouseholdApiService by lazy {
        retrofit.create(HouseholdApiService::class.java)
    }

    val scheduleService : ScheduleApiService by lazy {
        retrofit.create(ScheduleApiService::class.java)
    }
}