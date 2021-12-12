package com.example.mealplanner.data

import com.example.mealplanner.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://192.168.0.113:8000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>

    @GET("users/{id}")
    suspend fun get(@Path("id") userId:Number, @Header("Authorization") authorization:String):
            Response<UserResponse>
}

interface HouseholdApiService {
    @GET("users/{id}/households")
    suspend fun getHouseholds(@Path("id") userId:Number, @Header("Authorization") authorization:String):
            Response<HouseholdsResponse>

    @GET("households/{id}/schedules")
    suspend fun getSchedules(@Path("id") householdId: Number, @Header("Authorization") authorization:String):
            Response<SchedulesResponse>
}

interface ScheduleApiService {
    @GET("schedules/{id}/meals")
    suspend fun getMeals(@Path("id") scheduleId:Number, @Header("Authorization") authorization:String):
            Response<MealsResponse>
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