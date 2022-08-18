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

    @PUT("households/{householdId}/addMember/{userId}")
    fun addMember(@Path("householdId") householdId: Number, @Path("userId") userId: Int, @Header("Authorization") authorization:String):
            Call<MemberResponse>

    @PUT("households/{householdId}/removeMember/{userId}")
    fun removeMember(@Path("householdId") householdId: Number, @Path("userId") userId: Number, @Header("Authorization") authorization:String):
            Call<MemberResponse>

    @PUT("households/{householdId}")
    fun updateHousehold(@Path("householdId") householdId: Number, @Header("Authorization") authorization:String, @Body body:CreateHousehold):
            Call<HouseholdResponse>

    @POST("households/{userId}")
    fun createHousehold(@Path("userId") userId: Number, @Header("Authorization") authorization:String, @Body body:CreateHousehold):
            Call<HouseholdResponse>
}

interface UsersApiService {
    @GET("users/byEmail/{email}")
    fun getUserId(@Path("email") email:String, @Header("Authorization") authorization:String):
            Call<UserIdResponse>
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

    val userService: UsersApiService by lazy {
        retrofit.create(UsersApiService::class.java)
    }

    val scheduleService : ScheduleApiService by lazy {
        retrofit.create(ScheduleApiService::class.java)
    }
}